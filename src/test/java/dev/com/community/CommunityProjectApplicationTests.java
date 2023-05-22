package dev.com.community;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.util.DateUtil.now;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.com.community.community.model.BoardDTO;
import dev.com.community.community.entity.BoardEntity;
import dev.com.community.community.repository.BoardRepository;
import dev.com.community.community.model.BoardUpdateDTO;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc
class CommunityProjectApplicationTests {


	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	BoardRepository boardRepository;


	@BeforeEach
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.build();
		boardRepository.deleteAll();
	}

	private final String BOARD_URL = "/api/boards";
	private final String TITLE = "title";
	private final String CONTENT = "content";
	private final LocalDateTime CREATED_AT = LocalDateTime.now();


	@DisplayName("addBoard: 글 추가에 성공")
	@Test
	public void addBoard() throws Exception {
		// given
		final String url = BOARD_URL;
		final BoardDTO userRequest = new BoardDTO(TITLE, CONTENT,CREATED_AT);

		final String requestBody = objectMapper.writeValueAsString(userRequest);

		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
			.content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE));

		// then
		result.andExpect(status().isCreated());
		List<BoardEntity> boards = boardRepository.findAll();
		assertThat(boards.size()).isEqualTo(1);
		assertThat(boards.get(0).getTitle()).isEqualTo(TITLE);
		assertThat(boards.get(0).getContent()).isEqualTo(CONTENT);
	}


	@DisplayName("findAllBoards: 글 목록 조회 성공")
	@Test
	public void findAllBoards() throws Exception{
		//given : 글 저장
		final String url = BOARD_URL;
		boardRepository.save(BoardEntity.builder()
			.title(TITLE)
			.content(CONTENT)
			.build());
		//when : 목록 조회 API 호출
		final ResultActions resultActions = mockMvc.perform(get(url)
			.accept(MediaType.ALL));

		//then : 응답 코드가 200, 반환 값 중 0 번째 요소 content, title이 저장된 값과 같은지 확인
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].content").value(CONTENT))
			.andExpect(jsonPath("$[0].title").value(TITLE));
	}

	@DisplayName("findBoard: 글 조회 성공")
	@Test
	public void findArticle() throws Exception {
		// given
		final String url = BOARD_URL + "/{id}";
		BoardEntity savedBoard = boardRepository.save(BoardEntity.builder()
			.title(TITLE).content(CONTENT).build());

		//when
		final ResultActions resultActions = mockMvc.perform(get(url, savedBoard.getId()));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").value(CONTENT))
				.andExpect(jsonPath("$.title").value(TITLE));
	}

	@DisplayName("deleteBoard: 글 삭제 성공")
	@Test
	public void deleteBoard() throws Exception {
		//given
		final String url = BOARD_URL + "/{id}";

		BoardEntity savedBoard = boardRepository.save(BoardEntity.builder()
			.title(TITLE).content(CONTENT).build());

		// when
		mockMvc.perform(delete(url, savedBoard.getId()))
			.andExpect(status().isOk());

		// then
		List<BoardEntity> boards = boardRepository.findAll();
		assertThat(boards).asList().isEmpty();
	}

	@DisplayName("updateBoard: 글 수정 성공")
	@Test
	public void updateBoard() throws Exception {
		//given
		final String url = BOARD_URL + "/{id}";
		BoardEntity savedBoard = boardRepository.save(BoardEntity.builder()
			.title(TITLE).content(CONTENT).build());

		final String newTitle = "new"+TITLE;
		final String newContent = "new"+CONTENT;
		final LocalDateTime updatedAt = LocalDateTime.now();
		BoardUpdateDTO request = new BoardUpdateDTO(newTitle,newContent, updatedAt);

		//when
		ResultActions result = mockMvc.perform(put(url, savedBoard.getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)));

		//then
		result.andExpect(status().isOk());

		BoardEntity board = boardRepository.findById(savedBoard.getId()).get();

		assertThat(board.getTitle()).isEqualTo(newTitle);
		assertThat(board.getContent()).isEqualTo(newContent);
	}

}
