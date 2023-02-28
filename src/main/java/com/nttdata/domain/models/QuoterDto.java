package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoterDto {
  private int typeCurrency;
  private String name;
  private double buys;
  private double sale;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
