package com.humancloud.librarymanagementsystem.Service;

import com.humancloud.librarymanagementsystem.Controller.RandomIds;
import com.humancloud.librarymanagementsystem.Model.*;
import com.humancloud.librarymanagementsystem.Model.RequestBody.AddBookRequest;
import com.humancloud.librarymanagementsystem.Model.RequestBody.AddMemberRequest;
import com.humancloud.librarymanagementsystem.Model.RequestBody.NewTransactionRequest;
import com.humancloud.librarymanagementsystem.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    @Autowired
    private BooksRepo booksRepo;
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private PublisherRepo publisherRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private BookAuthorsRepo bookAuthorsRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private TransactionsRepo transactionsRepo;
    public ResponseEntity<Object> addBook(@RequestBody AddBookRequest addBookRequest)
    {
        RandomIds randomIds=new RandomIds();
        long bookId=randomIds.getRandomId();
        long authorId=randomIds.getRandomId();
        long genreId=randomIds.getRandomId();
        long publisherId=randomIds.getRandomId();

        Genres genres=new Genres();
        Genres gg;
        genres.setGenreId(genreId);
        genres.setGenreName(addBookRequest.getGenres().getGenreName());
        Optional<Genres> optionalGenres=Optional.ofNullable(genreRepo.findByName(addBookRequest.getGenres().getGenreName()));
        if(optionalGenres.isEmpty()) gg=genreRepo.save(genres);
        else gg=optionalGenres.get();


        Publishers publishers=new Publishers();
        publishers.setPublisherId(publisherId);
        publishers.setPublisherName(addBookRequest.getPublishers().getPublisherName());
        Publishers pp;
        Optional<Publishers> optionalPublishers=Optional.ofNullable(publisherRepo.findByName(addBookRequest.getPublishers().getPublisherName()));
        if(optionalPublishers.isEmpty()) pp = publisherRepo.save(publishers);
        else pp = optionalPublishers.get();


        Authors authors=new Authors();
        authors.setAuthorId(authorId);
        authors.setAuthorName(addBookRequest.getAuthor().getAuthorName());
        Optional<Authors> optionalAuthors=Optional.ofNullable(authorRepo.findByName(addBookRequest.getAuthor().getAuthorName()));
        Authors aa;
        if(optionalAuthors.isEmpty()) aa = authorRepo.save(authors);
        else aa=optionalAuthors.get();


        Books books=new Books();
        books.setBookId(bookId);
        books.setTitle(addBookRequest.getTitle());
        books.setAuthorName(addBookRequest.getAuthor().getAuthorName());
        books.setISBN(addBookRequest.getIsbn());
        books.setPublicationYear(addBookRequest.getPublicationYear());
        books.setGenres(gg);
        books.setPublishers(pp);
        books.setQuantity(1);
        Books bb = booksRepo.save(books);


        BookAuthors bookAuthors = new BookAuthors();
        bookAuthors.setBooks(bb);
        bookAuthors.setAuthors(aa);
        bookAuthorsRepo.save(bookAuthors);
        return ResponseEntity.ok("Book Saved Successfully!...");
    }

    public List<Books> getBooks()
    {
        return booksRepo.findAll();
    }

    public ResponseEntity<?> getBookByTitle(String bookName)
    {
        List<Books> books=booksRepo.findByName(bookName);
        if(books.isEmpty())
            try {
                throw new Exception("Sorry!...\nBook not found!");
            }
            catch(Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        else
            return ResponseEntity.ok(books);
    }
    public ResponseEntity<?> getBookByAuthor(String authorName)
    {
        List<Books> books=booksRepo.findByAuthor(authorName);
        if(books.isEmpty())
            try {
                throw new Exception("Sorry!...\nBook not found!");
            }
            catch(Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        else
            return ResponseEntity.ok(books);
    }
    public ResponseEntity<?> getBook(String bookName, String authorName)
    {
        List<Books> books=booksRepo.findBook(bookName,authorName);
        if(books.isEmpty())
            try {
                throw new Exception("Sorry!...\nBook not found!");
            }
            catch(Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        else
            return ResponseEntity.ok(books);
    }

    public String addMember(AddMemberRequest members)
    {
        RandomIds r=new RandomIds();
        long memId= r.getRandomId();

        Members m=new Members();
        m.setMemberId(memId);
        m.setMemberName(members.getMemberName());
        m.setAddress(members.getAddress());
        m.setPhone(members.getPhone());
        m.setEmail(members.getEmail());
        memberRepo.save(m);
        return "Member Added Successfully!...";
    }

    public String deleteMember(long memId)
    {
        memberRepo.deleteById(memId);
        return "Member Deleted Successfully!....";

    }

    public ResponseEntity<List<?>> getMemById(String memId)
    {
        Optional<Optional<Members>> m=Optional.ofNullable(memberRepo.findById(Long.parseLong(memId)));
        if(m.isPresent())
            return ResponseEntity.ok(Collections.singletonList(m.get()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("Member Not Found!..."));
    }

    public ResponseEntity<List<?>> getMembers()
    {
        return ResponseEntity.ok(memberRepo.findAll());
    }

    public Object borrow(NewTransactionRequest newTransactionRequest)
    {
        System.out.println("entered service");


        Optional<Members> mem=memberRepo.findById(newTransactionRequest.getMemId());
        Optional<Books> book=booksRepo.findById(newTransactionRequest.getBookId());
        if(mem.isPresent() && book.isPresent())
        {
            if(book.get().getQuantity()>0)
            {
                Books books=book.get();
                books.setQuantity(books.getQuantity()-1);
                Books bb=booksRepo.save(books);

                Members mm=mem.get();
                Members newMem = memberRepo.save(mm);

                Transactions transactions=new Transactions();

                transactions.setTransactionType(newTransactionRequest.getTransactionType());

                LocalDate currentDate = LocalDate.now();
                LocalDate dueDate = currentDate.plusDays(15);
                Date sqlDueDate = Date.valueOf(dueDate);

                transactions.setDueDate(sqlDueDate);
                transactions.setBooks(bb);
                transactions.setMembers(newMem);

                transactionsRepo.save(transactions);

                return "Book borrowed successfully!...\nDue date : "+dueDate;
            }
            else return "Sorry! Book not available at this moment.";
        }
        else return "Sorry! Book can not be borrowed !...\nKindly check Membership or Book availability.";
    }


    public Object returnBook(NewTransactionRequest newTransactionRequest)
    {
        Optional<Members> mem=memberRepo.findById(newTransactionRequest.getMemId());
        Optional<Books> book=booksRepo.findById(newTransactionRequest.getBookId());
        if(mem.isPresent() && book.isPresent())
        {
                Books books=book.get();
                books.setQuantity(books.getQuantity()+1);
                Books bb=booksRepo.save(books);

                Members mm=mem.get();
                Members newMem = memberRepo.save(mm);

                Transactions transactions=new Transactions();
                transactions.setTransactionType(newTransactionRequest.getTransactionType());

                LocalDate currentDate = LocalDate.now();
                Date sqlDueDate = Date.valueOf(currentDate);

                Optional<Transactions> trans= transactionsRepo.findByBookIdMemId(newTransactionRequest.getBookId(), newTransactionRequest.getMemId());
                transactions.setDueDate(trans.get().getDueDate());
                transactions.setReturnDate(sqlDueDate);
                transactions.setBooks(bb);
                transactions.setMembers(newMem);

                transactionsRepo.save(transactions);

                return "Book returned successfully!...";
        }
        else return "Your borrow entry not found!...";
    }

    public Object transactionByBookId(String bookId)
    {
        List<Transactions> transactions=transactionsRepo.findByBookId(Long.parseLong(bookId));
        if(transactions.isEmpty())
        {
            return "No transactions found for the provide book id!...";
        }
        else
        {
            return transactions;
        }
    }

    public Object transactionByMemId(String memId)
    {
        List<Transactions> transactions=transactionsRepo.findByMemId(Long.parseLong(memId));
        if(transactions.isEmpty())
        {
            return "No transactions found for the provide book id!...";
        }
        else
        {
            return transactions;
        }
    }

    public Object transactionByBookIdMemId(String bookId, String memId) {
        List<Transactions> transactions=transactionsRepo.findByBookId_MemId(Long.parseLong(bookId),Long.parseLong(memId));
        if(transactions.isEmpty())
        {
            return "No transactions found for the provide book id!...";
        }
        else
        {
            return transactions;
        }
    }

    public Object transactions() {
        return transactionsRepo.findAll();
    }
}
