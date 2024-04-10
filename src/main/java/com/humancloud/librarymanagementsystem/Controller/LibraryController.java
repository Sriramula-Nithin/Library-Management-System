package com.humancloud.librarymanagementsystem.Controller;

import com.humancloud.librarymanagementsystem.Model.Books;
import com.humancloud.librarymanagementsystem.Model.RequestBody.AddBookRequest;
import com.humancloud.librarymanagementsystem.Model.RequestBody.AddMemberRequest;
import com.humancloud.librarymanagementsystem.Model.RequestBody.NewTransactionRequest;
import com.humancloud.librarymanagementsystem.Service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/books")
    public ResponseEntity<Object> addBook(@RequestBody AddBookRequest addBookRequest)
    {
        return libraryService.addBook(addBookRequest);
    }

    @GetMapping("books")
    public List<Books> getBooks()
    {
        return libraryService.getBooks();
    }

    @GetMapping("book")
    public ResponseEntity<?> getBook(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName)
    {
         if(bookName !=null && authorName==null)
        {
            return libraryService.getBookByTitle(bookName);
        }
         else if(bookName ==null && authorName!=null)
         {
             return libraryService.getBookByAuthor(authorName);
         }
        else if(bookName != null && authorName != null)
        {
            return libraryService.getBook(bookName, authorName);
        }
        return null;
    }

    @PostMapping("members")
    public String addMembers(@RequestBody AddMemberRequest members)
    {
        return libraryService.addMember(members);
    }

    @DeleteMapping("members")
    public String deleteMember(@RequestParam long memId)
    {
        return libraryService.deleteMember(memId);
    }

    @GetMapping("members")
    public ResponseEntity<List<?>> getMembers(@RequestParam (required = false) String memId)
    {
        if(memId != null)
            return libraryService.getMemById(memId);

        return libraryService.getMembers();
    }

    @PostMapping("transaction")
    public Object newTransaction(@RequestBody NewTransactionRequest newTransactionRequest)
    {
        String transactionType= newTransactionRequest.getTransactionType();
        if(transactionType.equalsIgnoreCase("borrow"))
        {
            return libraryService.borrow(newTransactionRequest);
        }
        else if(transactionType.equalsIgnoreCase("return"))
        {
            return libraryService.returnBook(newTransactionRequest);
        }
        else return "Invalid Book Transaction!...";
    }

    @GetMapping("transaction")
    public Object getTransaction(@RequestParam(required = false) String  bookId, @RequestParam(required = false) String  memId)
    {
        if(bookId!=null && memId == null)
        {
            return libraryService.transactionByBookId(bookId);
        }
        if(bookId == null && memId != null)
        {
            return libraryService.transactionByMemId(memId);
        }
        if(bookId != null && memId != null)
            return libraryService.transactionByBookIdMemId(bookId,memId);

        return libraryService.transactions();
    }
}
