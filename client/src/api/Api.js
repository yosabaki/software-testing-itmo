import axios from 'axios'

const SERVER_URL = 'http://localhost:9000'

const instance = axios.create({
  baseURL: SERVER_URL,
  timeout: 1000
})

export default {
  // (C)reate
  createNewBook: (book) => {
    return instance.post('books', {
      title: book.title,
      description: book.description,
      readPages: book.readPages,
      totalPages: book.totalPages
    })
  },

  // (R)ead
  getAllBooks: () => {
    return instance.get('books', {
      transformResponse: [function (data) {
        return data ? JSON.parse(data)._embedded.books : data
      }]
    })
  },
  getBookById: (id) => {
    return instance.get('books/' + id, {
      transformResponse: [function (data) {
        return data ? JSON.parse(data) : data
      }]
    })
  },

  // (U)pdate
  updateBookForId: (id, book) => {
    return instance.put('books/' + id, {
      title: book.title,
      description: book.description,
      readPages: book.readPages,
      totalPages: book.totalPages
    })
  },

  // (D)elete
  removeBookForId: (id) => {
    return instance.delete('books/' + id)
  }
}
