package com.ttcsolutions.kwapp.core.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class AccountInfoResponse {
  Long id;
  String accountNo;
  String nickname;
  int level;
  long experience;

}
