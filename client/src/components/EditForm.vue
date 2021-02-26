<template>
  <div class="form-popup" id="myForm">
    <form class="form-container">
      <slot></slot>

      <div class="input element">
        <label><b>Title</b></label>
        <input :placeholder="book.title" v-model="book.title">
      </div>

      <div class="input element">
        <label><b>Description</b></label>
        <input :placeholder="book.description" v-model="book.description">
      </div>

      <div class="input element">
        <label><b>Pages</b></label>
        <input :placeholder="book.readPages" type="number" min="0" v-model="book.readPages">
        /
        <input :placeholder="book.totalPages" type="number" min="0" v-model="book.totalPages">
      </div>

      <footer>
        <button type="submit" class="btn" @click="$emit('editDone', book)">Save</button>
        <button class="btn cancel" @click="$emit('editCancel')">Cancel</button>
      </footer>
    </form>
  </div>
</template>

<script>
export default {
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
  }
}
</script>

<style scoped>

</style>
