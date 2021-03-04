<template>
  <label class="title"><b>{{ title }}</b></label>
  <label class="description">{{ description }}</label>
  <label class="pages">{{ readPages }}/{{ totalPages }}</label>
</template>

<script>
const Book = {
  name: 'Book',
  props: {
    title: String,
    description: String,
    readPages: Number,
    totalPages: Number
  }
}

export default Book
</script>

<style scoped>

</style>
