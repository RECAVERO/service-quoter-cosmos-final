package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.QuoterService;
import com.nttdata.domain.models.QuoterDto;
import com.nttdata.domain.models.RequestDto;
import com.nttdata.domain.models.ResponseDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/quoter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuoterResource {
  private final QuoterService quoterService;

  public QuoterResource(QuoterService quoterService) {
    this.quoterService = quoterService;
  }

  @GET
  @Timed(name = "wallet_list_quoter")
  @Counted(name = "count_list_quoter")
  public Multi<QuoterDto> findAll() {
    return quoterService.list();
  }

  @POST
  @Timed(name = "wallet_add_quoter")
  @Counted(name = "count_add_quoter")
  public Uni<QuoterDto> addQuoter(QuoterDto quoterDto) {
    return quoterService.addQuoter(quoterDto);
  }

  @PUT
  public Uni<QuoterDto> updateQuoter(QuoterDto quoterDto) {
    return quoterService.updateQuoter(quoterDto);
  }

  @POST
  @Path("/search")
  public Uni<QuoterDto> findByQuoter(QuoterDto quoterDto) {
    return quoterService.findByNroQuoter(quoterDto);
  }

  @DELETE
  public Uni<QuoterDto> deleteQuoter(QuoterDto quoterDto) {
    return quoterService.deleteQuoter(quoterDto);
  }

  @POST
  @Path("/convert")
  public Uni<ResponseDto> findByQuoterConvert(RequestDto requestDto) {
    ResponseDto responseDto = new ResponseDto();
    return quoterService.convertQuoter(requestDto).flatMap(c->{
      responseDto.setTypeCurrency(c.getTypeCurrency());
      responseDto.setMount(requestDto.getAmount());
      responseDto.setBuys(c.getBuys());
      responseDto.setSale(c.getSale());

      if(requestDto.getTypeCurrency() == 1){
        responseDto.setBootCoinConvert(requestDto.getAmount() / c.getSale());
        responseDto.setSolesConvert( requestDto.getAmount());
      }else{
        responseDto.setSolesConvert(requestDto.getAmount() * c.getBuys());
        responseDto.setBootCoinConvert( requestDto.getAmount());
      }

      return Uni.createFrom().item(responseDto);
    });
  }

}
