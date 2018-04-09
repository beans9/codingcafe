<template>
  <div class="hello">
    회원가입

    <p>아이디: <input type="text" v-model="user.username"/></p>
    <P>비밀번호: <input type="password" v-model="user.password" /></P>
    <input type="button" @click="proc()" value="회원가입"/>
  </div>
</template>

<script>
import {users} from '@/api/api'
export default {
  name: 'UserSignUp',
  data () {
    return {
      user: {
        username: '',
        password: ''
      }
    }
  },
  created: function () {},
  methods: {
    proc: function () {
      users.signUp(this.user).then((res) => {
        this.$router.push('/')
      })
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
