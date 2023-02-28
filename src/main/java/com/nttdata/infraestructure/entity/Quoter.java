package com.nttdata.infraestructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quoter {
  private int typeCurrency;
  private String name;
  private double buys;
  private double sale;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
