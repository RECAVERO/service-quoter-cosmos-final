package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.QuoterRepository;
import com.nttdata.domain.models.QuoterDto;
import com.nttdata.domain.models.RequestDto;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@ApplicationScoped
public class QuoterRepositoryImpl implements QuoterRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public QuoterRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }


  @Override
  public Multi<QuoterDto> list() {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("quoters");
    ReactiveMongoCollection<Document> collection = database.getCollection("quoter");
    return collection.find().map(doc->{
      QuoterDto quoterDto = new QuoterDto();
      quoterDto.setName(doc.getString("name"));
      quoterDto.setTypeCurrency(doc.getInteger("typeCurrency"));
      quoterDto.setBuys(doc.getDouble("buys"));
      quoterDto.setSale(doc.getDouble("sale"));
      quoterDto.setCreated_datetime(doc.getString("created_datetime"));
      quoterDto.setUpdated_datetime(doc.getString("updated_datetime"));
      quoterDto.setActive(doc.getString("active"));
      return quoterDto;
    }).filter(quoter->{
      return quoter.getActive().equals("S");
    });
  }

  @Override
  public Uni<QuoterDto> findByNroQuoter(QuoterDto quoterDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("quoters");
    ReactiveMongoCollection<Document> collection = database.getCollection("quoter");
    return collection
        .find(new Document("typeCurrency", 1)).map(doc->{
          QuoterDto quoter = new QuoterDto();
          quoter.setName(doc.getString("name"));
          quoter.setTypeCurrency(1);
          quoter.setBuys(doc.getDouble("buys"));
          quoter.setSale(doc.getDouble("sale"));
          quoter.setCreated_datetime(doc.getString("created_datetime"));
          quoter.setUpdated_datetime(doc.getString("updated_datetime"));
          quoter.setActive(doc.getString("active"));
          return quoter;
        }).filter(s->s.getActive().equals("S")).toUni();
  }

  @Override
  public Uni<QuoterDto> addQuoter(QuoterDto quoterDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("quoters");
    ReactiveMongoCollection<Document> collection = database.getCollection("quoter");
    Document document = new Document()
        .append("typeCurrency", quoterDto.getTypeCurrency())
        .append("name", quoterDto.getName())
        .append("buys", quoterDto.getBuys())
        .append("sale", quoterDto.getSale())
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");
    return collection.insertOne(document).replaceWith(quoterDto);
  }

  @Override
  public Uni<QuoterDto> updateQuoter(QuoterDto quoterDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("quoters");
    ReactiveMongoCollection<Document> collection = database.getCollection("quoter");

    Bson filter = eq("typeCurrency", quoterDto.getTypeCurrency());

    Bson updates = combine(
        set("buys", quoterDto.getBuys()),
        set("sale", quoterDto.getSale()),
        set("updated_datetime", this.getDateNow()));
    return collection.updateOne(filter,updates).replaceWith(quoterDto);
  }

  @Override
  public Uni<QuoterDto> deleteQuoter(QuoterDto quoterDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("quoters");
    ReactiveMongoCollection<Document> collection = database.getCollection("quoter");

    Bson filter = eq("typeCurrency", quoterDto.getTypeCurrency());
    Bson updates = combine(
        set("updated_datetime", quoterDto.getUpdated_datetime()),
        set("active", "N")
    );

    return collection.updateOne(filter,updates).replaceWith(quoterDto);
  }

  @Override
  public Uni<QuoterDto> convertQuoter(RequestDto requestDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("quoters");
    ReactiveMongoCollection<Document> collection = database.getCollection("quoter");
    return collection
        .find(new Document("typeCurrency", 1)).map(doc->{
          QuoterDto quoter = new QuoterDto();
          quoter.setName(doc.getString("name"));
          quoter.setTypeCurrency(doc.getInteger("typeCurrency"));
          quoter.setBuys(doc.getDouble("buys"));
          quoter.setSale(doc.getDouble("sale"));
          quoter.setCreated_datetime(doc.getString("created_datetime"));
          quoter.setUpdated_datetime(doc.getString("updated_datetime"));
          quoter.setActive(doc.getString("active"));
          return quoter;
        }).filter(s->s.getActive().equals("S")).toUni();
  }

  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
