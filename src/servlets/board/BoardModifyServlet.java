package servlets.board;

import java.net.URLEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAO;
import model.board.BoardModel;

//게시판 수정 폼
@WebServlet("/board/boardModifyServlet")
public class BoardModifyServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	/** BOARD DAO */
	private BoardDAO boardDAO = null;
    
    public BoardModifyServlet() {
        super();
    }
    
    //GET 접근
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException ,java.io.IOException {
    	// POST 한글 파라미터 깨짐 처리
    	request.setCharacterEncoding("UTF-8");
    	//파라미터
    	String num = request.getParameter("num");
		String subject = request.getParameter("subject");
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		String searchTextUTF8_E = URLEncoder.encode(searchText, "UTF-8");
		String ip = request.getRemoteAddr();
    	//모델
		BoardModel boardModel = new BoardModel();
		boardModel.setNum(Integer.parseInt(num));
		boardModel.setSubject(subject);
		boardModel.setWriter(writer);
		boardModel.setContents(contents);
		boardModel.setIp(ip);
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		//게시물 수정 처리
		this.boardDAO = new BoardDAO();
		this.boardDAO.update(boardModel);
		//페이지 이동
		response.sendRedirect(
				"boardViewServlet?num="+num+"&pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchTextUTF8_E);
    		
    }
   
    
}
