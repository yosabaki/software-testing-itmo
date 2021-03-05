<template>
  <div v-if="loading">
    <h1 class="loading">Loading...</h1>
  </div>
  <div v-else id="LibrarySection">
    <header class="header">
      <button v-if="!editMode" class="new-book" v-on:click="addBook">
          Add a new book
      </button>
    </header>
    <EditForm ref="editBook" v-if="editMode" :read-pages="0" :title="''" :description="''" :total-pages="0"
              @error-occurred="onErrorOccurred" @edit-cancel="onEditCancel" @edit-done="onEditDone"/>
    <section v-else class="main" v-show="books.length" v-cloak>
      <ul class="book-list">
        <li v-for="book in filteredBooks"
            class="book"
            :key="book.id"
            :class="{ completed: book.completed, inprogress: book.inprogress }">
          <div class="view">
            <div @click="viewBook(book)" class="book">
              <Book :description="book.description"
                    :readPages="book.readPages"
                    :title="book.title"
                    :totalPages="book.totalPages"></Book>
            </div>
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
          <li><label @click="$emit('logout')"
                     class="logout">Logout</label>
          </li>
        </ul>
      </footer>
    </section>
  </div>
</template>

<script>
import api from '../api/Api'
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
    api.getAllBooks()
      .then(response => {
        this.$logger.debug('Data loaded: ', response.data)
        if (Array.isArray(response.data)) {
          this.books = response.data
        } else {
          this.books = response.data.data
        }
      })
      .catch(error => {
        this.$logger.debug(error.message)
        this.$emit('errorOccurred', 'Failed to load books')
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
      api.removeBookForId(book.id).then(() => { // notice AM using "=>" syntax
        this.$logger.debug('Item removed:', book)
        console.log(this.books)
        this.books.splice(this.books.indexOf(book), 1)
        console.log(this.books)
      }).catch(error => {
        this.$logger.debug(error)
        this.$emit('errorOccurred', 'Failed to remove book')
      })
    },
    viewBook: function (book) {
      this.$router.push('/book/' + book.id)
    },
    onEditCancel: function () {
      this.editMode = false
    },
    onEditDone: function (book) {
      this.$logger.debug(book)
      this.editMode = false
      api.createNewBook(book)
        .then(response => {
          this.$logger.info('New data created: ', response.data)
          this.books.push(response.data)
        })
        .catch(error => {
          this.$logger.debug(error)
          this.$emit('errorOccurred', 'Failed to create a new book')
        })
    },
    onErrorOccurred: function (error) {
      console.log('you are here')
      this.$emit('errorOccurred', error)
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
