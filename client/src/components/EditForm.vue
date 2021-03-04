<template>
  <div class="editForm" id="myForm">
    <div class="title element">
      <input id="edit-title" placeholder="Title" v-model="book.title">
    </div>

    <div class="description element">
      <textarea id="edit-description" placeholder="Description" v-model="book.description" rows="3"></textarea>
    </div>

    <div class="pages element">
      <label><b>Pages</b></label>
      <input id="edit-readPages" :placeholder="book.readPages" type="number" min="0" v-model="book.readPages">
      /
      <input id="edit-totalPages" :placeholder="book.totalPages" type="number" min="0" v-model="book.totalPages">
    </div>
  </div>
  <header class="header">
    <button id="saveEditForm" class="new-book" v-on:click="onEditSave">
      Save
    </button>
    <button id="cancelEditForm" class="new-book" @click="$emit('editCancel')">Cancel</button>
  </header>
</template>

<script>
import { errorMessages } from '@/assets/resources'

const EditForm = {
  name: 'EditForm',
  props: {
    title: String,
    description: String,
    readPages: Number,
    totalPages: Number
  },
  data () {
    return {
      book: {
        title: this.title,
        description: this.description,
        readPages: this.readPages,
        totalPages: this.totalPages
      }
    }
  },
  methods: {
    onEditSave: function () {
      if (this.book.readPages < 0 || this.book.totalPages < 0) {
        this.$emit('errorOccurred', errorMessages.lessThanZero)
      } else if (this.book.readPages > this.book.totalPages) {
        this.$emit('errorOccurred', errorMessages.readGreaterThanTotal)
      } else if (this.book.title === '') {
        this.$emit('errorOccurred', errorMessages.requiredTitle)
      } else if (this.book.title.length > 255) {
        this.$emit('errorOccurred', errorMessages.tooLongTitle)
      } else if (this.book.description.length > 1000) {
        this.$emit('errorOccurred', errorMessages.tooLongDescription)
      } else {
        this.$emit('editDone', this.book)
      }
    }
  }
}

export default EditForm
</script>

<style scoped>

</style>
