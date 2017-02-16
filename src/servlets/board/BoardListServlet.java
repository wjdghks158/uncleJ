package servlets.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAO;
import model.board.BoardModel;
import util.PageNavigator;

//게시판 목록 페이지 서블릿 클래스
@WebServlet("/board/boardListServlet")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/** BOARD DAO */
	private BoardDAO boardDAO = null;
    
    public BoardListServlet() {
        super();
    }
    
    //GET 접근 시
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		if (pageNum == null) {
			pageNum = "1";
		}
		if (searchText == null) {
			searchType = "";
			searchText = "";
		}
		String searchTextUTF8 = new String(searchText.getBytes("ISO-8859-1"), "UTF-8");
		// 모델
		BoardModel boardModel = new BoardModel();
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchTextUTF8);
		
		this.boardDAO = new BoardDAO();
		
		int totalCount = this.boardDAO.selectCount(boardModel);
		
		List<BoardModel> boardList = this.boardDAO.selectList(boardModel);
		//View 사용될 객체 설정
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageNavigator", new PageNavigator().getPageNavigator(totalCount, boardModel.getListCount(), boardModel.getPagePerBlock(), 
				Integer.parseInt(pageNum), searchType, searchTextUTF8));
		request.setAttribute("boardList", boardList);
		request.setAttribute("boardModel",boardModel);
		//View 보내기
		RequestDispatcher requestDispatcher =
				request.getRequestDispatcher("/WEB-INF/jsps/board/boardList.jsp");
		requestDispatcher.forward(request, response);
		
    	
    }
}
