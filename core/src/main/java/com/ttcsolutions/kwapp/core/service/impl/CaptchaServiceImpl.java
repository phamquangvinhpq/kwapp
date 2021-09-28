package com.ttcsolutions.kwapp.core.service.impl;

import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.core.model.response.CaptchaResponse;
import com.ttcsolutions.kwapp.core.service.CaptchaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Log4j2
public class CaptchaServiceImpl implements CaptchaService {
  private final JedisPool jedisPool;
  private final String MAP_NAME = "Captcha";
  private final String[] operators = {"+", "-", "*"};
  private final Duration captchaTtl;

  public CaptchaServiceImpl(JedisPool jedisPool,
                            @Value("${kwapp.captcha.ttl}") Duration captchaTtl) {
    this.jedisPool = jedisPool;
    this.captchaTtl = captchaTtl;
  }

  @Override
  public CaptchaResponse get() {
    var expression = generateExpression();
    CaptchaResponse captchaResponse = new CaptchaResponse();
    try (Jedis jedis = jedisPool.getResource()) {
      captchaResponse.setCaptcha(convertToImage(String.format("%s = ?", expression)));
      var result = evaluateExpression(expression);
      var captchaId = UUID.randomUUID().toString();
      captchaResponse.setExpiredAt(OffsetDateTime.now().plus(captchaTtl));
      captchaResponse.setCaptchaId(captchaId);
      var jedisKey = String.format("%s:%s", MAP_NAME, captchaId);
      jedis.set(jedisKey, Integer.toString(result));
      jedis.expireAt(captchaId, captchaTtl.toMillis());
    }
    catch (IOException e) {
      log.error(e);
    }
    return captchaResponse;
  }

  @Override
  public void verify(String captchaId, Integer answer) {
    var jedisKey = String.format("%s:%s", MAP_NAME, captchaId);
    try (Jedis jedis = jedisPool.getResource()) {
      var savedResult = jedis.get(jedisKey);
      if(Objects.isNull(savedResult) || !Integer.valueOf(savedResult).equals(answer)){
        log.error("{} is wrong captcha, correct one is {}", answer, savedResult);
        throw new BusinessException(ErrorCode.WRONG_CAPTCHA, "You have sent wrong captcha");
      }
      jedis.del(jedisKey);
    }

  }

  private byte[] convertToImage(String text) throws IOException {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);// Represents an image with 8-bit RGBA color components packed into integer pixels.
    Graphics2D graphics2d = image.createGraphics();
    Font font = new Font("TimesNewRoman", Font.BOLD, 24);
    graphics2d.setFont(font);
    FontMetrics fontmetrics = graphics2d.getFontMetrics();
    int width = fontmetrics.stringWidth(text);
    int height = fontmetrics.getHeight();
    graphics2d.dispose();

    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    graphics2d = image.createGraphics();
    graphics2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    graphics2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    graphics2d.setFont(font);
    fontmetrics = graphics2d.getFontMetrics();
    graphics2d.setColor(Color.BLACK);
    graphics2d.drawString(text, 0, fontmetrics.getAscent());
    graphics2d.dispose();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    return baos.toByteArray();
  }

  private String generateExpression() {
    var firstOperand = ThreadLocalRandom.current().nextInt(10);
    var operator = operators[ThreadLocalRandom.current().nextInt(operators.length)];
    var secondOperand = ThreadLocalRandom.current().nextInt(10);
    return String.format("%d %s %d", firstOperand, operator, secondOperand);
  }

  private Integer evaluateExpression(String expression) {
    ExpressionParser parser = new SpelExpressionParser();
    return parser.parseExpression(expression).getValue(Integer.class);
  }
}
