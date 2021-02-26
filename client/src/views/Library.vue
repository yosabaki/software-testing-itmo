<template>
  <div v-if="loading">
    <h1 class="loading">Loading...</h1>
  </div>
  <div v-else>
    <header class="header">
      <button class="new-book" v-if="!editMode"
              v-on:click="addBook()">Add a new book
      </button>
      <EditForm :read-pages="0" :title="''" :description="''" :total-pages="0"
        v-else @edit-cancel="onEditCancel" @edit-done="onEditDone"></EditForm>
    </header>
    <section class="main" v-show="books.length" v-cloak>
      <ul class="book-list">
        <li v-for="book in filteredBooks"
            class="book"
            :key="book.id"
            :class="{ completed: book.completed, inprogress: book.inprogress }">
          <div class="view">
            <Book :description="book.description"
                  :readPages="book.readPages"
                  :title="book.title"
                  :totalPages="book.totalPages"
                  @click="viewBook(book)"></Book>
            <button class="destroy" @click="removeBook(book)"></button>
          </div>
        </li>
      </ul>
      <footer class="footer" v-show="books.length" v-cloak>
          <span class="book-count">
            <strong>{{ remaining }}</strong> {{ pluralize }} are in progress
          </span>
        <ul class="filters">
          <li><label @click="setVisibility('all')" :class="{ selected: visibility === 'all' }">All</label></li>
          <li><label @click="setVisibility('inprogress')"
                     :class="{ selected: visibility === 'inprogress' }">In progress</label></li>
          <li><label @click="setVisibility('completed')"
                     :class="{ selected: visibility === 'completed' }">Completed</label>
          </li>
        </ul>
      </footer>
    </section>
  </div>
</template>

<script>
import api from '../Api'
import Book from '@/components/Book'
import EditForm from '@/components/EditForm'
// visibility filters
const filters = {
  all: function (books) {
    return books
  },
  inprogress: function (books) {
    return books.filter(function (book) {
      return book.readPages < book.totalPages
    })
  },
  completed: function (books) {
    return books.filter(function (book) {
      return book.readPages === book.totalPages
    })
  }
}
// app Vue instance
const Library = {
  name: 'Library',
  components: {
    Book: Book,
    EditForm: EditForm
  },
  props: {
    activeUser: Object
  },

  data () {
    return {
      books: [],
      visibility: 'all',
      loading: true,
      editMode: false,
      error: null
    }
  },
  mounted () {
    api.getAll()
      .then(response => {
        this.$log.debug('Data loaded: ', response.data)
        this.books = response.data
      })
      .catch(error => {
        this.$log.debug(error)
        this.error = 'Failed to load books'
      })
      .finally(() => {
        this.loading = false
      })
  },

  computed: {
    filteredBooks: function () {
      return filters[this.visibility](this.books)
    },
    remaining: function () {
      return filters.inprogress(this.books).length
    },
    pluralize: function () {
      return this.remaining === 1 ? 'book' : 'books'
    },
    allDone: {
      get: function () {
        return this.remaining === 0
      },
      set: function (value) {
        this.books.forEach(function (todo) {
          todo.completed = value
        })
      }
    }
  },

  methods: {
    addBook: function () {
      this.editMode = true
    },
    setVisibility: function (vis) {
      this.visibility = vis
    },
    removeBook: function (book) { // notice NOT using "=>" syntax
      api.removeForId(book.id).then(() => { // notice AM using "=>" syntax
        this.$log.debug('Item removed:', book)
        this.books.splice(this.books.indexOf(book), 1)
      }).catch(error => {
        this.$emit('errorOccurred', error)
      })
    },
    viewBook: function (book) {
      this.$router.push('/book/' + book.id)
    },
    onEditCancel: function () {
      this.editMode = false
    },
    onEditDone: function (book) {
      this.$logger.debug('assss')
      this.$logger.debug(book)
      this.editMode = false
      api.createNew(book)
        .then(response => {
          this.$log.info('New data created: ', response.data)
          this.books.push({
            id: response.data.id,
            title: book.title,
            description: book.description,
            readPages: book.readPages,
            totalPages: book.totalPages
          })
        })
        .catch(error => {
          this.$emit('errorOccurred', error)
        })
    }
  },

  // a custom directive to wait for the DOM to be updated
  // before focusing on the input field.
  // http://vuejs.org/guide/custom-directive.html
  directives: {
    'todo-focus': function (el, binding) {
      if (binding.value) {
        el.focus()
      }
    }
  }
}
export default Library
</script>

<style>
[v-cloak] {
  display: none;
}
</style>
