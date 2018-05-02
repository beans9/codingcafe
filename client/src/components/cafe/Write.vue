<template>
  <div class="hello">
    {{title}}
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <div>사진
      <div v-for="(photo,index) in files" v-bind:key="index" v-bind:class="{active:photo.default}">
        <img :src="photo.image" class="img" @click="changeDefaultImg(photo)"/>
        <input type="button" value="삭제" @click="delFile(photo)" />
        {{photo.default}}
      </div>

      <input type="file" id="file" ref="file" multiple @change="handleFilesUpload($event, files)" style="display:none"/>
      <input type="button" value="사진업로드" @click="photoUpload()" />
    </div>
    <input type="button" @click="proc()" value="저장"/>
  </div>
</template>

<script>
// import _ from 'lodash'
import {cafes} from '@/api/api'
export default {
  name: 'CafeWrite',
  data () {
    return {
      title: '입력',
      cafe: {
        name: '',
        memo: ''
      },
      files: []
    }
  },
  created: function () {
    // this.addFile()
  },
  methods: {
    proc: function () {
      cafes.insert(this.cafe, this.files).then((res) => {
        setTimeout(() => {
          this.$router.push('/cafe/' + res.id)
        }, 1000)
      })
    },
    addFile: function (image, file) {
      let def = this.files.filter((data) => data.default === true).length === 0
      this.files.push({image: image, default: def, file: file})
    },
    delFile: function (photo) {
      if (photo.default === true) {
        if (this.files.length > 0) {
          this.files[0].default = true
        }
      }
      let photoIdx = this.files.indexOf(photo)
      this.files.splice(photoIdx, 1)
    },
    changeDefaultImg: function (photo) {
      this.files.filter((data) => data.default === true)[0].default = false
      photo.default = true
    },
    handleFilesUpload: function (e, photos) {
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
    photoUpload: function () {
      this.$refs.file.value = ''
      this.$refs.file.click()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

.img {
  width:200px;
  border:1px solid #e6e6e6;
}

.active {
  border:2px solid red;
}
</style>
