<template>
  <div v-if="loading">
    <h1 class="loading">Loading...</h1>
  </div>
  <div v-else>
    <section class="bookView">
      <div v-if="!editMode">
        <Book :title="book.title" :description="book.description" :readPages="book.readPages"
              :totalPages="book.totalPages"></Book>
        <button v-on:click="editBook">Edit Book</button>
      </div>
      <EditForm @focus="focus = true"
                @blur="focus = false" v-else v-bind="book" @edit-cancel="onEditCancel" @edit-done="onEditDone">Edit Book</EditForm>
    </section>
  </div>
</template>

<script>
import Book from '@/components/Book'
import EditForm from '@/components/EditForm'
import api from '../Api'

export default {
  name: 'ViewBook',
  components: {
    Book, EditForm
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
    api.getById(this.$route.params.id)
      .then(response => {
        this.$log.debug('Data loaded: ', response.data)
        this.book = response.data
      })
      .catch(error => {
        this.$log.debug(error)
        this.error = 'Failed to load todos'
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
      api.updateForId(this.book.id, book)
        .then(response => {
          this.$log.info('Data updated: ', response.data)
          this.book = response.data
        })
        .catch(error => {
          this.$log.error(error)
          this.$emit('errorOccurred', error.toString())
        })
    }
  }
}
</script>

<style scoped>

</style>
