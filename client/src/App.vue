<template>
  <div class="title-container">
    <h1 class="title" @click="$router.push('/')">Library App</h1>
    <h1 class="email">{{userEmail}}</h1>
  </div>
  <section class="libraryApp">
  <router-view @error-occurred="onErrorOccurred"/>
  </section>
  <div v-if="error" class="error" @click="handleErrorClick">
    ERROR: {{error}}
  </div>
</template>
<script>
import Library from '@/views/Library'
import ViewBook from '@/views/ViewBook'
import('./assets/style.css')

export default {
  name: 'App',
  components: [
    Library, ViewBook
  ],
  data () {
    return {
      loading: true
    }
  },
  computed: {
    userEmail: function () {
      return this.activeUser ? this.activeUser.email : ''
    },
    inputPlaceholder: function () {
      return this.activeUser ? this.activeUser.given_name + ', what needs to be done?' : 'What needs to be done?'
    }
  },
  methods: {
    handleErrorClick: function () {
      this.error = null
    },
    onErrorOccurred: function (error) {
      this.$log.debug(error)
      this.error = error
    },
    onLoaded: function () {
      this.loading = false
    }
  }
}

</script>

<style>
</style>
