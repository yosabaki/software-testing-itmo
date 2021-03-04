<template>
  <div class="title-container">
    <h1 class="title" @click="logoClick">Library App</h1>
    <h1 class="email">{{userEmail}}</h1>
  </div>
  <section class="libraryApp">
  <router-view :key="$route.fullPath" ref="childComponent" @error-occurred="onErrorOccurred"/>
  </section>
  <footer>
  <div v-if="error" class="error" @click="handleErrorClick">
    ERROR: {{error}}
  </div>
  </footer>
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
      loading: true,
      error: null
    }
  },
  computed: {
    userEmail: function () {
      return this.activeUser ? this.activeUser.email : ''
    }
  },
  methods: {
    logoClick: function () {
      if (this.$route.fullPath === '/') {
        this.$router.go(0)
      } else {
        this.$router.push('/')
      }
    },
    handleErrorClick: function () {
      this.error = null
    },
    onErrorOccurred: function (error) {
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
