<template>
  <div class="editForm" id="registerForm">
    <div class="username element">
      <input id="edit-username" placeholder="Username" v-model="username">
    </div>

    <div class="password element">
      <input id="edit-password" type="password" placeholder="Password" v-model="password">
    </div>
  </div>
  <header class="header">
    <button id="saveEditForm" class="new-book" @click="onLogin">Login</button>
    <button id="cancelEditForm" class="new-book" @click="onRegister">Register</button>
  </header>
</template>

<script>
import { errorMessages } from '@/assets/resources'

export default {
  name: 'RegistrationForm',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    checkForm () {
      if (this.username.length > 40) {
        this.$emit('errorOccurred', errorMessages.tooLongUsername)
      } else if (this.password.length > 40) {
        this.$emit('errorOccurred', errorMessages.tooLongPassword)
      } else if (this.password.length < 6) {
        this.$emit('errorOccurred', errorMessages.tooShortPassword)
      } else if (!this.username.match(/^[A-Za-z0-9]+$/)) {
        this.$emit('errorOccurred', errorMessages.invalidUsername)
      } else {
        return true
      }
      return false
    },
    onLogin: function () {
      this.$logger.debug('you are here login')
      if (this.checkForm()) {
        this.$emit('login', this.username, this.password)
      }
    },
    onRegister: function () {
      this.$logger.debug('you are here login')
      if (this.checkForm()) {
        this.$emit('register', this.username, this.password)
      }
    }
  }
}
</script>

<style scoped>

</style>
