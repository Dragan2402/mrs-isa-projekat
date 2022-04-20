<template>
  <div class="main">
    <button class="btn btn-primary" @click="goToUserProfile()">User Profile</button>
    <button class="btn btn-primary" @click="goToFishingClassProfile()">Fishing Class Profile</button>
    <button class="btn btn-primary" @click="goToVacationHousesProfile()">Vacation House Profile</button>
    <br>
    <div class="entities-div">
      <div>
        <input class="input-group-text" type="text" placeholder="Filter by name, address..." v-model="filter"/>
      </div>
      <br>
      <div class="radio-div">
        <input class="radio" type="radio" id="contactChoice1"
               name="choice" value=0 v-model="displayType">
        <label class="radio-label" for="contactChoice1"> <img class="radio-img" src="../../public/house.png"> Houses</label>
        <input class="radio" type="radio" id="contactChoice2"
               name="choice" value=1 v-model="displayType">
        <label class="radio-label" for="contactChoice2"><img class="radio-img" src="../../public/boat.png"> Boats</label>
        <input class="radio" type="radio" id="contactChoice3"
               name="choice" value=2 v-model="displayType">
        <label class="radio-label" for="contactChoice3"><img class="radio-img" src="../../public/fishing.png"> Fishing classes</label>
      </div>
      <br>
      <div v-if="this.displayType==0">
        <div class="list-entities" v-for="(vacationHouse, index) in this.filteredVacationHouses" @Click="selectEntity(vacationHouse)"
            v-bind:index="index" :key="vacationHouse.id" v-bind="{selected: selectedEntity.id===vacationHouse.id}">
          <div class="entity-picture"><img src="https://dirstcanoerental.com/images/uploads/20190523//800_66c5cc36bf6ec1257baacaec58d223370b37279b.jpg"/></div>
          <div class="entity-name"><h3>{{ vacationHouse.name }}</h3>
            <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> {{ vacationHouse.address }} </div>
            <div class="entity-description">{{ vacationHouse.promoDescription }}</div>
          </div>
          <div class="entity-price">
            <div><h5>{{ vacationHouse.priceList }}</h5></div>
            <div><button class="btn btn-warning" @click="jumpToPreview(vacationHouse)">Explore</button></div>
          </div>
        </div>

      </div>
      <div v-if="this.displayType==1">
        <div class="list-entities" v-for="(ship,index) in this.filteredShips" @Click="selectEntity(ship)" v-bind:index="index" :key="ship.id"
            v-bind="{selected: selectedEntity.id===ship.id}">
        <div class="entity-picture"><img src="http://www.lejournalinternational.info/wp-content/uploads/2018/10/Titanic_II-1320x564.jpg"/></div>
          <div class="entity-name"><h3>{{ ship.name }}</h3>
            <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> {{ ship.address }} </div>
            <div class="entity-description">{{ship.promoDescription}}</div>
          </div>
          <div class="entity-price">
            <div><h5>{{ ship.priceList }}</h5></div>
            <div><button class="btn btn-warning" @click="jumpToPreview(ship)">Explore</button></div>
          </div>
        </div>
      </div>
      <div v-if="this.displayType==2">
        <div class="list-entities" v-for="(fishingClass,index) in this.filteredFishingClasses" @Click="selectEntity(fishingClass)"
             v-bind:index="index" :key="fishingClass.id" v-bind="{selected: selectedEntity.id===fishingClass.id}">
          <div class="entity-picture"><img src="http://www.listribolov.co.rs/images/za_textove/377/bistravodavelika.JPG"/></div>
          <div class="entity-name"><h3>{{ fishingClass.name }}</h3>
            <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> {{ fishingClass.address }} </div>
            <div class="entity-description">{{fishingClass.promoDescription}}</div>
          </div>
          <div class="entity-price">
            <div><h5>{{ fishingClass.priceList }}</h5></div>
            <div><button class="btn btn-warning" @click="jumpToPreview(fishingClass)">Explore</button></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {

  name: "HomePage",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      fishingClasses: [],
      vacationHouses: [],
      ships: [],
      selectedEntity: {},
      selectedRows: [],
      filter: "",
      selected: false,
      displayType: 0,  //represents type of renting entity, 0 vac houses, 1 ships , 2 fishing classes
    }
  }
  ,
  mounted() {
    axios.get("api/vacation_houses/all").then(response => this.vacationHouses=response.data);
    axios.get("api/fishingClasses/all").then(response => this.fishingClasses = response.data);
    axios.get("api/ships/all").then(response => this.ships = response.data);

  },
  computed: {
    filteredVacationHouses() {
      return this.vacationHouses.filter(row => {
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();


        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
    filteredShips() {
      return this.ships.filter(row => {
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();


        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
    filteredFishingClasses() {
      return this.fishingClasses.filter(row => {
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();


        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
  },
  methods: {  
        

    goToUserProfile() {
      this.$router.push('/clientProfile');
    },
    goToFishingClassProfile() {
      this.$router.push('/fishingClassProfile');
    },
    goToVacationHousesProfile() {
      this.$router.push({
        name: "vacationHouses",
        params: {id: 3}
      });
    },
    selectEntity(entity) {

      this.selectedEntity = entity;
      this.selected = true;

    },
    jumpToPreview(entity) {
      this.selectEntity(entity);
      this.$router.push({
        name: "rentingEntityPreview",
        params: {id: this.selectedEntity.id, displayType: this.displayType}
      });
    }

  }
}
</script>


<style scoped>

.main {
  margin: 10px;
}

.input-group-text {
  width: 100%;
  margin: auto;
}

button {
  margin: 20px;
}

.list-entities {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
}

.entity-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 200px;
  height: 150px;
}

.entity-picture {
  float:left;
}

.entity-name {
  float: left;
  margin: 10px;
  text-align: left;
}

.entity-description {
  margin-left: 5px;
  color: #585858;
  max-width: 280px;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.entity-price {
  margin: 40px;
  float: right;
}

.main {
  text-align: center;
  margin: auto;
  width: 50%;
  padding: 10px;
}

.radio {
  margin: 20px;
  opacity: 0;
  width: 0;
  height: 0;
}

.radio-img {
  width: 50px;
}

.radio-label {
  padding: 5px;
  height: 60px;
}

.radio-div{
  text-align: left;
  margin-left: 200px;
}

.radio-label:hover{
  cursor: pointer;
}

[type=radio]:checked + label {
  outline: 2px solid darkgrey;
  border-radius: 5px;
  transform: scale(1.2);
  transition: all 0.1s ease;
}

</style>