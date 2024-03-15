package lk.ijse.repository.custom;

import lk.ijse.entity.Book;
import lk.ijse.repository.CRUDRepository;

public interface BookRepository extends CRUDRepository<Book> {
    String getBranchId(int bId);

    Book getBook(int bookId);
}
