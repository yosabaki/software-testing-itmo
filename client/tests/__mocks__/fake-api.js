const initialBooks = [
  {
    id: '1',
    title: 'eat smth',
    description: '',
    readPages: 0,
    totalPages: 10
  },
  {
    id: '2',
    title: 'kill smone',
    description: '',
    readPages: 0,
    totalPages: 10
  }
]

let books = [...initialBooks]

export default {
  reloadBooks: () => {
    books = [...initialBooks]
  },
  // (C)reate
  createNewBook: (book) => {
    return new Promise(resolve => {
      const newBook = {
        id: books.length,
        title: book.title,
        description: book.description,
        readPages: book.readPages,
        totalPages: book.totalPages
      }
      books.push(newBook)
      resolve({ data: newBook })
    })
  },

  // (R)ead
  getAllBooks: () => {
    return new Promise(resolve => {
      resolve({ data: [...books] })
    })
  },
  getBookById: async (id) => {
    return new Promise(resolve => {
      resolve({
        data: books.filter(book => {
          return book.id === id
        })[0]
      })
    })
  },

  // (U)pdate
  updateBookForId: async (id, book) => {
    return new Promise((resolve, reject) => {
      for (let i = 0; i < books.length; i++) {
        if (books[i].id === id) {
          books[i] = book
          resolve(book)
        }
      }
      reject(new Error(''))
    })
  },

  // (D)elete
  removeBookForId: async (id) => {
    return new Promise((resolve, reject) => {
      for (let i = 0; i < books.length; i++) {
        if (books[i].id === id) {
          books.splice(i, 1)
          resolve()
        }
      }
      reject(new Error(''))
    })
  }
}
