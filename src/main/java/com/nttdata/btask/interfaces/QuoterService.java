package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.QuoterDto;
import com.nttdata.domain.models.RequestDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface QuoterService {
  Multi<QuoterDto> list();

  Uni<QuoterDto> findByNroQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> addQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> updateQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> deleteQuoter(QuoterDto quoterDto);

  Uni<QuoterDto> convertQuoter(RequestDto requestDto);
}
