package igazhituek.kereszt;

import lombok.Data;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class KeresztApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void contextLoads() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void searchallTest() throws Exception {
        String url = new StringBuilder().append("/api/user/searchall").toString();
        mockMvc.perform(post(url)).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void searchTest() throws Exception {
        String url = new StringBuilder().append("/api/user/search/").toString();
        String username = "Shaq";
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(username)).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void messagesTest() throws Exception {
        String url = new StringBuilder().append("/api/chat/messages").toString();
        mockMvc.perform(get(url).param("sender", "0").param("receiver", "2"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void addMessagesTest() throws Exception {
        String url = new StringBuilder().append("/api/chat/savemessage").toString();
        mockMvc.perform(post(url).param("sender", "1").param("receiver", "2").param("message", "szioka"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void likeTest() throws Exception {
        String url = new StringBuilder().append("/api/user/like").toString();
        mockMvc.perform(post(url).param("userID", "0").param("likedId", "2"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void dislikeTest() throws Exception {
        String url = new StringBuilder().append("/api/user/dislike").toString();
        mockMvc.perform(post(url).param("userID", "2").param("likedId", "1"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void getnotlikedTest() throws Exception {
        String url = new StringBuilder().append("/api/user/notliked").toString();
        mockMvc.perform(get(url).param("userID", "2"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void matchesTest() throws Exception {
        String url = new StringBuilder().append("/api/user/matches").toString();
        mockMvc.perform(get(url).param("userID", "2"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Data
    static class RegModel {

        public String username;
        public String picture;
        public String birth;
        public String email;
        public String password;
        public String felekezet;
        public String where;
        public String sex;
        public String about;
    }

    @Test
    public void registerTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegModel m = new RegModel();
        m.setEmail("test@gmail.com");
        m.setBirth("22");
        m.setUsername("Test");
        m.setPassword("asdf");
        m.setAbout("test");
        m.setFelekezet("test");
        String url = new StringBuilder().append("/api/user/register").toString();
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(m)))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

}
