<template>
  <div class="hello">
    상세화면
    <p>
      ID: {{id}}
    </p>
    <p>
      이름 : {{cafe.name}}
    </p>
    <p>
      설명 : {{cafe.memo}}
    </p>
    <input type="button" @click="patch()" value="수정"/>
    <input type="button" @click="del()" value="삭제"/>
    <router-link to="/">리스트</router-link>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
export default {
  name: 'CafeDetail',
  props: ['id'],
  data () {
    return {
      cafe: {
        name: '',
        memo: ''
      }
    }
  },
  beforeRouteEnter (to, from, next) {
    cafes.get(to.params.id).then((res) => {
      next(vm => { vm.cafe = res })
    }).catch((e) => { next(false) })
  },
  watch: {
    '$route': function (from, to) {
      this.init()
    }
  },
  created: function () {},
  methods: {
    init: function () {
      cafes.get(this.id).then((res) => {
        this.cafe = res
      }).catch((e) => {
        this.$router.push('/')
      })
    },
    patch: function () {
      this.$router.push('/cafe/update/' + this.id)
    },
    del: function () {
      cafes.del(this.id).then(() => {
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
