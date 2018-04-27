<template>
  <div class="hello">
    {{title}}
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <div>사진
      <div>
        <div v-for="(photo,index) in photos" v-if="photo.image != null" v-bind:key="index" v-bind:class="{active:photo.isDefault}">
          <img :src="photo.image" class="img" @click="changeDefaultImg(photo)"/>
          <input type="button" value="삭제" @click="delFile(photo)" />
        </div>
      </div>
      <div v-for="(file,index) in photos" v-bind:key="index" >
        <input type="file" id="files" ref="files" @change="handleFilesUpload($event, file)" v-show="file.isView"/>
        <br/>
      </div>
      <!-- <input type="button" value="파일추가" @click="addFile()"/> -->
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
      photos: [],
      photosIdx: 0,
      defultImgIdx: 0
    }
  },
  created: function () {
    this.addFile()
  },
  methods: {
    proc: function () {
      let defaultImgIdx = 0
      for (let i = 0; i < this.photos.length; i++) {
        if (this.photos[i].isDefault === true) {
          defaultImgIdx = i
          break
        }
      }

      cafes.insert(this.cafe, this.$refs.files, defaultImgIdx).then((res) => {
        setTimeout(() => {
          this.$router.push('/cafe/' + res.id)
        }, 1000)
      })
    },
    addFile: function () {
      this.photos.push({idx: this.photosIdx++, isView: true, image: null, isDefault: false})
    },
    delFile: function (photo) {
      if (photo.isDefault === true) {
        this.photos.filter((data) => data.image !== null)[0].isDefault = true
      }

      let photoIdx = this.photos.indexOf(photo)
      this.photos.splice(photoIdx, 1)
    },
    changeDefaultImg: function (photo) {
      if (this.photos.filter((data) => data.isDefault === true).length > 0) {
        this.photos.filter((data) => data.isDefault === true)[0].isDefault = false
      }
      photo.isDefault = true
    },
    handleFilesUpload: function (e, file) {
      var files = e.target.files || e.dataTransfer.files
      if (!files.length) {
        return
      }
      // let image = new Image()
      let reader = new FileReader()
      let vm = this
      reader.onload = (e) => {
        file.image = e.target.result
        file.isView = false
        if (this.photos.filter((data) => data.isDefault === true).length === 0) {
          file.isDefault = true
        }
        vm.addFile()
      }
      reader.readAsDataURL(files[0])
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
