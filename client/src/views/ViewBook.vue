<template>
  <div v-if="loading">
    <h1 class="loading">Loading...</h1>
  </div>
  <div v-else id="bookViewSection">
    <button v-if="!editMode" class="new-book" v-on:click="editBook">
      Edit Book
    </button>
    <section v-if="!editMode" class="bookView">
      <div class="bookHolder">
        <Book :title="book.title" :description="book.description" :readPages="book.readPages"
              :totalPages="book.totalPages"></Book>
      </div>
    </section>
    <EditForm ref="editBook" v-else v-bind="book" @error-occurred="onErrorOccurred" @edit-cancel="onEditCancel" @edit-done="onEditDone"></EditForm>
  </div>
</template>

<script>
import Book from '@/components/Book'
import EditForm from '@/components/EditForm'
import api from '../api/Api'

const ViewBook = {
  name: 'ViewBook',
  components: {
    Book, EditForm
  },
  props: {
    id: Number
  },
  data () {
    return {
      loading: true,
      book: null,
      error: null,
      editMode: false
    }
  },
  mounted () {
    api.getBookById(this.id)
      .then(response => {
        this.$log.debug('Data loaded: ', response.data)
        this.book = response.data
      })
      .catch(error => {
        this.$log.debug(error)
        this.$emit('errorOccurred', 'Failed to load book')
      })
      .finally(() => {
        this.$logger.info('')
        this.loading = false
      })
  },
  methods: {
    editBook: function () {
      this.editMode = true
    },
    onEditCancel: function () {
      this.$logger.debug('canceled')
      this.editMode = false
    },
    onEditDone: function (book) {
      this.editMode = false
      this.$logger.debug(book)
      api.updateBookForId(this.book.id, book)
        .then(response => {
          this.$log.info('Data updated: ', response.data)
          this.book = response.data
        })
        .catch(error => {
          this.$log.error(error)
          this.$emit('errorOccurred', 'Failed to update book')
        })
    },
    onErrorOccurred: function (error) {
      this.$emit('errorOccurred', error)
    }
  }
}

export default ViewBook
</script>

<style scoped>

</style>
