package com.ttcsolutions.kwapp.commons.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.experimental.Accessors;

@CompiledJson
@Data
@Accessors(chain = true)
public class KwAccount {
  private Long id;
  private String email;
}
