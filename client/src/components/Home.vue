<template>
  <el-container>
    <el-header>
      <el-row :gutter="24">
        <el-col :span="5" :offset="1">
          <router-link to="/cafe"><div class="grid-content tal">Coding Cafe</div></router-link>
        </el-col>
        <el-col :span="5" :offset="12">
          <div class="grid-content">
            <router-link to="/user/signin" v-if="!isAuthenticated">로그인</router-link>
            <router-link to="/user/signup" v-if="!isAuthenticated">회원가입</router-link>

            <el-dropdown v-if="isAuthenticated" size="small" style="padding-right:10px;" @command="loginInfoCommand">
              <span class="el-dropdown-link">
                {{getUserInfo.name}} <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="info">Info</el-dropdown-item>
                <el-dropdown-item command="logout">Logout</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <router-link to="/cafe/new" v-if="isAuthenticated"><el-button icon="el-icon-edit" size="mini" v-if="isAuthenticated">입력</el-button></router-link>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer>
      <el-row :gutter="24">
        <el-col :span="23" :offset="1">
          <div class="grid-content">
            © CoadingCaffe. All rights reserved
          </div>
        </el-col>
      </el-row>
    </el-footer>

    <el-dialog
      title="Insert"
      :visible.sync="newDialogFlag"
      width="50%"
      :before-close="handleClose">
      <cafe-form></cafe-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
import CafeList from '@/components/cafe/List.vue'
// import {EventBus} from '@/event-bus.js'
import CafeForm from '@/components/cafe/Write.vue'
import {mapGetters} from 'vuex'

export default {
  name: 'Home',
  components: {CafeList, CafeForm},
  data () {
    return {
      newDialogFlag: true
    }
  },
  created: function () {},
  methods: {
    loginInfoCommand: function (command) {
      if (command === 'logout') {
        delete localStorage.codingcafeToken
        this.$store.dispatch('LOGOUT')
        this.$router.push('/cafe')
      } else if (command === 'info') {
        this.$router.push('/user/info')
      }
    }
  },
  computed: {
    ...mapGetters([
      'isAuthenticated', 'getUserInfo'
    ])
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
@import "~styles/common.scss";
@import "~styles/_mixin.scss";
header{
  padding-top:20px;
  padding-bottom:20px;
}
footer{
  line-height: 60px;
  text-align: left;
  font-size: 12px;
}

</style>
