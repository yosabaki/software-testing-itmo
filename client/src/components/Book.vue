<template>
  <div class="book">
    <label class="title"><b>{{ title }}</b></label>
    <label class="description">{{ description }}</label>
    <label class="pages">{{ readPages }}/{{ totalPages }}</label>
  </div>
</template>

<script>
export default {
  name: 'Book',
  props: {
    title: String,
    description: String,
    readPages: Number,
    totalPages: Number
  },
  data () {
    return {
      editMode: false
    }
  },
  computed: {
    inprogress: function () {
      return this.readPages < this.totalPages
    },
    completed: function () {
      return this.readPages === this.totalPages
    }
  }
}
</script>

<style scoped>

</style>
