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
    }, { withCredentials: true })
  },

  register: (username, password) => {
    return instance.post('users/register', {
      username: username,
      password: password
    }, { withCredentials: true })
  },

  // (R)ead
  getAllBooks: () => {
    return instance.get('books', {
      transformResponse: [function (data) {
        return data ? JSON.parse(data) : data
      }],
      withCredentials: true
    })
  },

  checkConnection: () => {
    return instance.get('users/', { withCredentials: true })
  },

  getBookById: (id) => {
    return instance.get('books/' + id, {
      transformResponse: [function (data) {
        return data ? JSON.parse(data) : data
      }],
      withCredentials: true
    })
  },

  logout: () => {
    return instance.post('/users/logout', {}, { withCredentials: true })
  },

  login: (username, password) => {
    return instance.post('/users/login', {
      username: username,
      password: password
    }, { withCredentials: true })
  },

  // (U)pdate
  updateBookForId: (id, book) => {
    return instance.put('books/' + id, {
      title: book.title,
      description: book.description,
      readPages: book.readPages,
      totalPages: book.totalPages
    }, { withCredentials: true })
  },

  // (D)elete
  removeBookForId: (id) => {
    return instance.delete('books/' + id, { withCredentials: true })
  }
}
