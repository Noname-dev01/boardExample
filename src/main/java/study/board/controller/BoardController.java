package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.Service.BoardService;
import study.board.entity.Board;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){

        boardService.write(board);
        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list", boardService.boardList());
        return "boardList";
    }

    @GetMapping("/board/view")
    public String boardViewForm(Model model,Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModifyForm(@PathVariable("id") Integer id,Model model){
        Board board = boardService.boardView(id);

        BoardForm form = new BoardForm();
        form.setId(board.getId());
        form.setTitle(board.getTitle());
        form.setContent(board.getContent());

        model.addAttribute("form",form);
        return "boardmodify";
    }

    @PostMapping("/board/modify/{id}")
    public String boardModify(@PathVariable Integer id,@ModelAttribute("form") BoardForm form){
        Board board = new Board();
        board.setId(form.getId());
        board.setContent(form.getContent());
        board.setTitle(form.getTitle());

        boardService.boardModify(id,board);

        return "redirect:/board/list";
    }
}
