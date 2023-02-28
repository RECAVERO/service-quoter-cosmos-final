package com.nttdata.domain.contract;

import com.nttdata.domain.models.QuoterDto;
import com.nttdata.domain.models.RequestDto;
import com.nttdata.infraestructure.entity.Quoter;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface QuoterRepository {
  Multi<QuoterDto> list();

  Uni<QuoterDto> findByNroQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> addQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> updateQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> deleteQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> convertQuoter(RequestDto requestDto);
}
