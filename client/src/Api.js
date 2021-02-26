import axios from 'axios'

const SERVER_URL = 'http://localhost:9000'

const instance = axios.create({
  baseURL: SERVER_URL,
  timeout: 1000
})

export default {
  // (C)reate
  createNew: (book) => {
    return instance.post('books', {
      title: book.title,
      description: book.description,
      readPages: book.readPages,
      totalPages: book.totalPages
    })
  },

  // (R)ead
  getAll: () => {
    return instance.get('books', {
      transformResponse: [function (data) {
        return data ? JSON.parse(data)._embedded.books : data
      }]
    })
  },
  getById: (id) => {
    return instance.get('books/' + id, {
      transformResponse: [function (data) {
        return data ? JSON.parse(data) : data
      }]
    })
  },

  // (U)pdate
  updateForId: (id, book) => {
    return instance.put('books/' + id, {
      title: book.title,
      description: book.description,
      readPages: book.readPages,
      totalPages: book.totalPages
    })
  },

  // (D)elete
  removeForId: (id) => {
    return instance.delete('books/' + id)
  }
}
