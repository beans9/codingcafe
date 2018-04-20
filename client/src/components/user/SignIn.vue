<template>
  <div class="hello">
    로그인

    <p>아이디: <input type="text" v-model="user.username"/></p>
    <P>비밀번호: <input type="password" v-model="user.password" /></P>

    <p v-if="loginErrorFlag">아이디와 비밀번호를 확인하십시오</p>
    {{loginErrorFlag}}
    <input type="button" @click="proc()" value="로그인"/>

  </div>
</template>

<script>
import {users} from '@/api/api'
export default {
  name: 'UserSignIn',
  data () {
    return {
      user: {
        username: 'b@b.com',
        password: '1'
      },
      loginErrorFlag: false
    }
  },
  created: function () {},
  methods: {
    proc: function () {
      users.signIn(this.user).then((res) => {
        this.loginErrorFlag = false
        this.$router.push('/')
      }).catch((e) => {
        this.loginErrorFlag = true
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
