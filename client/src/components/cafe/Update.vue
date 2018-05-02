<template>
  <div class="hello">
    {{title}}
    <p>ID: {{id}}</p>
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <div>사진
      <div>
        <!-- 기존사진 -->
        <div v-for="(photo,index) in files" v-bind:key="index" v-bind:class="{active:photo.default, delete:photo.isDelete}">
          <img :src="imgSrc(photo)" class="img" @click="changeDefaultImg(photo)" />
          <input type="button" :value="delBtnTitle(photo.isDelete)" @click="delFile(photo)" />
        </div>
        <input type="file" id="file" ref="file" multiple @change="handleFilesUpload($event, files)" style="display:none"/>
        <input type="button" value="사진업로드" @click="photoUpload()" />
      </div>
    </div>

    <input type="button" @click="proc()" value="저장"/>
    <input type="button" @click="back()" value="뒤로"/>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
export default {
  name: 'CafeUpdate',
  props: ['id'],
  data () {
    return {
      title: '수정',
      cafe: {
        id: null,
        name: '',
        memo: ''
      },
      files: []
    }
  },
  beforeRouteEnter (to, from, next) {
    cafes.get(to.params.id).then((res) => {
      next(vm => {
        vm.cafe = res
        for (let i = 0; i < vm.cafe.photos.length; i++) {
          vm.addDbFile(vm.cafe.photos[i])
        }
      })
    }).catch((e) => { next(false) })
  },
  watch: {
    '$route': function (from, to) { this.get() }
  },
  created: function () {},
  methods: {
    get: function () {
      cafes.get(this.id).then((res) => {
        this.cafe = res
      }).catch((e) => {
        this.$router.push('/')
      })
    },
    proc: function () {
      cafes.patch(this.cafe, this.files).then((res) => {
        this.$router.replace('/cafe/' + res.id)
      })
    },
    back: function () {
      this.$router.go(-1)
    },
    addDbFile: function (file) {
      let def = this.files.filter((data) => data.default === true).length === 0
      this.files.push({id: file.id, image: null, default: def, file: null, isDelete: false, name: file.name})
    },
    addFile: function (image, file) {
      let def = this.files.filter((data) => data.default === true).length === 0
      this.files.push({id: null, image: image, default: def, file: file, isDelete: false, name: null})
    },
    delFile: function (photo) {
      if (photo.default === true) {
        let filterData = this.files.filter((data) => data.isDelete !== true && data.default === false)
        if (filterData.length > 0) filterData[0].default = true
        photo.default = false
      }

      if (photo.id !== null) {
        photo.isDelete = !photo.isDelete
        if (photo.isDelete === false && this.files.filter((data) => data.default === true).length === 0) {
          photo.default = true
        }
      } else {
        this.files.splice(this.files.indexOf(photo), 1)
      }
    },
    changeDefaultImg: function (photo) {
      if (photo.isDelete) return
      this.files.filter((data) => data.default === true)[0].default = false
      photo.default = true
    },
    handleFilesUpload: function (e, file) {
      let files = e.target.files || e.dataTransfer.files
      if (!files.length) {
        return
      }

      let vm = this
      for (let i = 0; i < files.length; i++) {
        let reader = new FileReader()
        reader.onload = (e) => {
          vm.addFile(e.target.result, files[i])
        }
        reader.readAsDataURL(files[i])
      }
    },
    imgSrc: function (photo) {
      if (photo.name === null) {
        return photo.image
      } else {
        return require('@/assets/images/' + photo.name)
      }
    },
    photoUpload: function () {
      this.$refs.file.value = ''
      this.$refs.file.click()
    },
    delBtnTitle: function (flag) {
      return flag ? '취소' : '삭제'
    }
  },
  computed: {}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

.img {
  width: 200px;
}

.active {
  border:2px solid red;
}

.delete {
  opacity: 0.5;
}
</style>
