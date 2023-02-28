package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.QuoterService;
import com.nttdata.domain.contract.QuoterRepository;
import com.nttdata.domain.models.QuoterDto;
import com.nttdata.domain.models.RequestDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuoterServiceImpl implements QuoterService {
  private final QuoterRepository quoterRepository;

  public QuoterServiceImpl(QuoterRepository quoterRepository) {
    this.quoterRepository = quoterRepository;
  }


  @Override
  public Multi<QuoterDto> list() {
    return quoterRepository.list();
  }

  @Override
  public Uni<QuoterDto> findByNroQuoter(QuoterDto quoterDto) {
    return quoterRepository.findByNroQuoter(quoterDto);
  }

  @Override
  public Uni<QuoterDto> addQuoter(QuoterDto quoterDto) {
    return quoterRepository.addQuoter(quoterDto);
  }

  @Override
  public Uni<QuoterDto> updateQuoter(QuoterDto quoterDto) {
    return quoterRepository.updateQuoter(quoterDto);
  }

  @Override
  public Uni<QuoterDto> deleteQuoter(QuoterDto quoterDto) {
    return quoterRepository.deleteQuoter(quoterDto);
  }

  @Override
  public Uni<QuoterDto> convertQuoter(RequestDto requestDto) {
    return quoterRepository.convertQuoter(requestDto);
  }
}
