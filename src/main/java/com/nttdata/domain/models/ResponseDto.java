package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
  private int typeCurrency;
  private double mount;
  private double buys;
  private double sale;
  private double solesConvert;
  private double bootCoinConvert;
}
