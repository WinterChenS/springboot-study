package com.winter.es;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RestController
public class ElasticsearchStudyApplication {

	@Autowired
	private TransportClient client;

	@GetMapping("/")
	public String index(){
		return "index";
	}

	@GetMapping("/get/book/novel")
	public ResponseEntity get(@RequestParam(name="id",defaultValue = "")String id){

		if(id.isEmpty()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		GetResponse rs = this.client.prepareGet("book", "novel", id)
				.get();
		if(!rs.isExists()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(rs.getSource(), HttpStatus.OK);
	}

	@PostMapping("/add/book/novel")
	public ResponseEntity add(@RequestParam(name="title")String title,
			@RequestParam(name="author")String author,
			@RequestParam(name = "word_count")int word_count,
			@RequestParam(name = "publish_date")
					@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
					String publish_date
			){
		try {
			XContentBuilder context = XContentFactory.jsonBuilder()
					.startObject()
					.field("title", title)
					.field("author", author)
					.field("word_count", word_count)
					.field("publish_date", publish_date)
					.endObject();
			IndexResponse rs = this.client.prepareIndex("book", "novel")
					.setSource(context)
					.get();
			return new ResponseEntity(rs,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/book/novel")
	public ResponseEntity delete(@RequestParam(name = "id")String id){
		try{
			DeleteResponse rs = this.client.prepareDelete("book", "novel", id)
					.get();

			return new ResponseEntity(rs, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/book/novel")
	public ResponseEntity update(
			@RequestParam(name="id")String id,
			@RequestParam(name="title", required = false)String title,
			@RequestParam(name="word_count", required = false)Integer wordCount,
			@RequestParam(name = "author", required = false)String author,
			@RequestParam(name = "publish_date", required = false)
					@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
					String publishDate
	){
		UpdateRequest update = new UpdateRequest("book", "novel", id);
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject();
			if (!title.isEmpty()){
				builder.field("title", title);
			}
			if (wordCount != null){
				builder.field("word_count", wordCount);
			}
			if(!author.isEmpty()){
				builder.field("author", author);
			}
			if(!publishDate.isEmpty()){
				builder.field("publish_date", publishDate);
			}
			builder.endObject();

			update.doc(builder);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			UpdateResponse response = this.client.update(update).get();
			return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/query/book/novel")
	public ResponseEntity query(
			@RequestParam(name="author", required = false) String author,
			@RequestParam(name="gt_word_count", defaultValue = "0") Integer gtWordCount,
			@RequestParam(name="lt_word_count", required = false) Integer ltWordCount,
			@RequestParam(name="title", required = false) String title
	){
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		if(author != null){
			boolQuery.must(QueryBuilders.matchQuery("author", author));
		}

		if(title != null){
			boolQuery.must(QueryBuilders.matchQuery("title", title));
		}

		RangeQueryBuilder wordCount = QueryBuilders.rangeQuery("word_count")
				.from(gtWordCount);

		if(ltWordCount != null && ltWordCount > 0){
			wordCount.to(ltWordCount);
		}

		boolQuery.filter(wordCount);
		SearchRequestBuilder builder = this.client.prepareSearch("book")
				.setTypes("novel")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQuery)
				.setFrom(0)
				.setSize(10);
		System.out.println(builder);
		SearchResponse searchResponse = builder.get();
		List<Map<String, Object>> list = new ArrayList<>();

		for (SearchHit hit : searchResponse.getHits()) {
			list.add(hit.getSource());
		}

		return new ResponseEntity(list, HttpStatus.OK);
	}

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchStudyApplication.class, args);
	}
}
