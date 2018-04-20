<template>
  <div class="hello">
    <p v-if="loginFlag">{{userInfo.name}}({{userInfo.email}})님 안녕하세요.</p>
    <div class="menu">
      <router-link to="/cafe/write" v-if="loginFlag">입력</router-link>
      <router-link to="/user/signin" v-if="!loginFlag">로그인</router-link>
      <router-link to="/user/signup" v-if="!loginFlag">회원가입</router-link>
      <router-link to="/user/info" v-if="loginFlag">회원정보</router-link>
      <a href="#" @click="logout()" v-if="loginFlag">로그아웃</a>
    </div>
    <CafeList></CafeList>
  </div>
</template>

<script>
import CafeList from '@/components/cafe/List.vue'
import {EventBus} from '@/event-bus.js'

export default {
  name: 'Home',
  components: {CafeList},
  data () {
    return {
      loginFlag: false,
      userInfo: {
        name: '',
        email: ''
      }
    }
  },
  created: function () {
    EventBus.$on('loginProc', this.init)
    this.init()
  },
  methods: {
    init: function () {
      const {codingcafeToken} = localStorage
      const {codingcafeName} = localStorage
      const {codingcafeEmail} = localStorage
      this.userInfo.name = codingcafeName
      this.userInfo.email = codingcafeEmail
      if (!codingcafeToken) {
        this.loginFlag = false
      } else {
        this.loginFlag = true
      }
    },
    logout: function () {
      delete localStorage.codingcafeToken
      this.init()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
