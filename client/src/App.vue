<template>
  <div class="title-container">
    <h1 class="title" @click="logoClick">Library App</h1>
    <h1 id="username" class="username">{{ username }}</h1>
  </div>
  <section class="libraryApp">
    <RegistrationForm @login="onLogin"
                      @register="onRegister"
                      @error-occurred="onErrorOccurred"
                      v-if="username == null"></RegistrationForm>
    <router-view v-else @logout="onLogout" :key="$route.fullPath" ref="childComponent"
                 @error-occurred="onErrorOccurred"/>
  </section>
  <footer>
    <div id="error" v-if="error" class="error" @click="handleErrorClick">
      ERROR: {{ error }}
    </div>
  </footer>
</template>
<script>
import RegistrationForm from '@/components/RegistrationForm'
import api from '@/api/Api'

import('./assets/style.css')

export default {
  name: 'App',
  components: {
    RegistrationForm: RegistrationForm
  },
  data () {
    return {
      loading: true,
      error: null,
      username: null
    }
  },
  mounted () {
    api.checkConnection()
      .then(response => {
        this.$logger.debug('Data loaded: ', response.data)
        this.username = response.data.username
      })
      .catch(error => {
        this.$logger.debug(error.message)
        this.$emit('errorOccurred', 'Failed to load books')
      })
  },
  methods: {
    logoClick: function () {
      if (this.$route.fullPath === '/') {
        this.$router.go(0)
      } else {
        this.$router.push('/')
      }
    },
    onLogout: function () {
      api.logout()
        .then(response => {
          this.$logger.debug('logout')
          this.username = response.data.username
        })
    },
    onLogin: function (username, password) {
      this.$logger.debug(username, password)
      api.login(username, password)
        .then(response => {
          this.$logger.debug(response)
          this.username = response.data.username
        })
    },
    onRegister: function (username, password) {
      api.register(username, password)
        .then(response => {
          this.$logger.debug(response)
          this.username = response.data.username
        })
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
