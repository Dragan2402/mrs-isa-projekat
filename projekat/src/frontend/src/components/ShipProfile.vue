<template>
  <div class="main-container">
    <div class="heading-flex">
      <div class="heading-left">
        <h3 class="main-heading">{{ship.name}}</h3>
        <vue3-star-ratings class="star-ratings" v-model="ship.rating" starSize="22"  :showControl=false :disableClick=true :step=0 />
        <h5 class="star-heading">({{ship.reviewsNumber}})</h5>
      </div>
      <div class="heading-right">
        <div class="heading-right-buttons">
          <button style="margin-right: 20px" type="button" @click="toggleEdit()" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modal">Edit info</button>
          <button type="button" @click="toggleCreatingOffer()" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalOffer">Create offer</button>
        </div>
      </div>
    </div>
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="carousel-image d-block w-100" alt="Active picture" v-bind:src="'data:image/jpeg;base64,' + pictures.at(0)">
        </div>
        <div class="carousel-item" v-for="(picture, index) in pictures.slice(1)" :key="index">
          <img class="carousel-image d-block w-100" alt="Item picture" v-bind:src="'data:image/jpeg;base64,' + picture">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <div class="main-description">
      <div class="inner-description">
        <div><b>Address: </b> {{ship.address}}</div>
        <div><b>Description: </b> {{ship.promoDescription}}</div>
        <div><b>Behaviour rules: </b> {{ship.behaviourRules}}</div>
        <div><b>Price: </b> {{ship.priceList}} per day</div>
        <div><b>Additional info: </b>{{ship.additionalInfo}}</div>
        <div><b>Cancellation conditions: </b>{{ship.cancellationConditions}}</div>
        <div><b>Type: </b>{{ship.type}}</div>
        <div><b>Length: </b>{{ship.length}}</div>
        <div><b>Engine Number: </b>{{ship.engineNumber}}</div>
        <div><b>Engine Power: </b>{{ship.enginePower}}</div>
        <div><b>Top Speed: </b>{{ship.topSpeed}}</div>
        <div><b>Client Limit: </b>{{ship.clientLimit}}</div>
        <div><b>Availability:</b> From {{ship.availableFrom}} to {{ship.availableTo}}</div>
      </div>
      <div class="google-map-container">
        <iframe class="google-map" v-bind:src="'https://maps.google.com/maps?q=' + ship.address + '&t=&z=13&ie=UTF8&iwloc=&output=embed'"></iframe>
      </div>
    </div>
    <hr>
<!--    UBACITI KALENDAR OVDE-->
  </div>
  <div class="modal fade" id="modal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input v-model="ship.name" type="text" class="form-control" id="floatName" placeholder="Title">
            <label for="floatName">Title</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.address" type="text" class="form-control" id="floatAddress" placeholder="Address">
            <label for="floatAddress">Address</label>
          </div>
          <div class="form-floating mb-3">
            <textarea style="height: 100px" v-model="ship.promoDescription" type="text" class="form-control" id="floatPromoDescription" placeholder="Description"></textarea>
            <label for="floatPromoDescription">Description</label>
          </div>
          <div class="form-floating mb-3">
            <textarea style="height: 100px" v-model="ship.behaviourRules" type="text" class="form-control" id="floatBehaviourRules" placeholder="Behaviour rules"></textarea>
            <label for="floatBehaviourRules">Behaviour rules</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.priceList" type="text" class="form-control" id="floatPriceList" placeholder="Price">
            <label for="floatPriceList">Price</label>
          </div>
          <div class="form-floating mb-3">
            <textarea style="height: 100px" v-model="ship.additionalInfo" type="text" class="form-control" id="floatAdditionalInfo" placeholder="Additional info"></textarea>
            <label for="floatAdditionalInfo">Additional info</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.cancellationConditions" type="text" class="form-control" id="floatCancellationConditions" placeholder="Cancellation conditions">
            <label for="floatCancellationConditions">Cancellation conditions</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.type" type="text" class="form-control" id="floatType" placeholder="Type">
            <label for="floatType">Type</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.length" type="text" class="form-control" id="flotLength" placeholder="Length">
            <label for="flotLength">Type</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.engineNumber" type="text" class="form-control" id="floatEngineNumber" placeholder="Engine number">
            <label for="floatEngineNumber">Engine number</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.enginePower" type="text" class="form-control" id="floatEnginePower" placeholder="Engine power">
            <label for="floatEnginePower">Engine power</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.topSpeed" type="text" class="form-control" id="floatTopSpeed" placeholder="Top speed">
            <label for="floatTopSpeed">Top speed</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="ship.clientLimit" type="text" class="form-control" id="floatClientLimit" placeholder="Client limit">
            <label for="floatClientLimit">Client limit</label>
          </div>
          <Datepicker v-model="availabilityInterval" :format="formatRange" range/>
        </div>
        <div class="modal-footer">
          <button type="button" @click="cancel()" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
          <button type="button" @click="saveEdit()" class="custom-btn button-primary" data-bs-dismiss="modal">Save changes</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="modalOffer" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input v-model="offer.clientLimit" type="text" class="form-control" id="floatClientLimitOffer" placeholder="Client limit">
            <label for="floatClientLimitOffer">Client limit</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="offer.price" type="text" class="form-control" id="floatPrice" placeholder="Price">
            <label for="floatPrice">Price</label>
          </div>
          <div class="mb-3">
            Additional Services:
            <vue3-tags-input style="height: 100px" :tags="offer.additionalServices" @on-tags-changed="updateTags" class="form-control" aria-label="Username" aria-describedby="additionalServicesOffer"/>
          </div>
<!--          IZMENITI NA NOVI DATEPICKER-->
          <Datepicker v-model="offerInterval" :format="formatRange" range/>
        </div>
        <div class="modal-footer">
          <button type="button" @click="cancel()" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
          <button type="button" @click="createOffer()" class="custom-btn button-primary" data-bs-dismiss="modal">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {useRoute} from "vue-router";
import {getCurrentInstance, onMounted, ref} from "vue";
import Vue3TagsInput from 'vue3-tags-input';

export default {
  name: "ShipProfile",
  components: {
    Vue3TagsInput
  },
  setup() {
    const route = useRoute();
    const toast = getCurrentInstance().appContext.config.globalProperties.$toast;

    let editing = ref(false);
    let creatingOffer = ref(false);

    let id = ref(1);
    let ship = ref({
      id: Number,
      name: String,
      address: String,
      promoDescription: String,
      availableFrom: String,
      availableTo: String,
      behaviourRules: String,
      priceList: String,
      additionalInfo: String,
      cancellationConditions: String,
      type: String,
      length: Number,
      engineNumber: String,
      enginePower: Number,
      topSpeed: Number,
      clientLimit: Number
    });
    let pictures = ref([]);
    let availabilityInterval = ref([]);

    let offer = ref({
      start: String,
      duration: Number,
      clientLimit: "",
      additionalServices: [],
      price: "",

      id: null,
      place: ship.value.address,
      rentingEntityId: ship.value.id
    });
    let offerInterval = ref([]);

    let tags = ref([]);

    onMounted(() => {
      if (route.params.id !== undefined) {
        id.value = route.params.id;
      }
      axios
          .get(`/api/ships/${id.value}`)
          .then(response => {
            ship.value = response.data;
            availabilityInterval.value = [stringToDateTime(ship.value.availableFrom),
                                          stringToDateTime(ship.value.availableTo)];
          });
      axios
          .get(`/api/ships/${id.value}/pictures/all`)
          .then(response => {
            for (let i = 0; i < response.data.length; i++) {
              pictures.value.push(response.data[i]);
            }
          });
    });

    function toggleEdit() {
      editing.value = true;
    }

    function toggleCreatingOffer() {
      creatingOffer.value = true;
    }

    function cancel() {
      editing.value = false;
      creatingOffer.value = false;
    }

    function saveEdit(){
      ship.value.availableFrom = dateTimeToString(availabilityInterval.value[0]);
      ship.value.availableTo = dateTimeToString(availabilityInterval.value[1]);

      axios
          .put(`/api/ships/${id.value}`, ship.value)
          .then(response => {
            ship.value = response.data;
            toast.success("Info updated");
          });

      editing.value = false;
    }

    function createOffer() {
      offer.value.start = dateTimeToString(offerInterval.value[0]);
      offer.value.duration = offerInterval.value[1] - offerInterval.value[0];
      offer.value.place = ship.value.address;
      offer.value.rentingEntityId = parseInt(id.value);

      axios
          .post("/api/offers/new", offer.value)
          .then(response => {
            offer.value = response.data;
            toast.success("Info updated");
          });

      creatingOffer.value = false;
    }

    function updateTags(tags) {
      offer.value.additionalServices = tags;
    }

    function stringToDateTime(s) {
      let tokens = s.split(" ");
      let date = tokens[0];
      let time = tokens[1];

      tokens = date.split(".");
      let day = parseInt(tokens[0]);
      let month = parseInt(tokens[1]) - 1;
      let year = parseInt(tokens[2]);

      tokens = time.split(":");
      let hour = parseInt(tokens[0]);
      let minute = parseInt(tokens[1]);

      return new Date(year, month, day, hour, minute);
    }

    function dateTimeToString(dt) {
      let day = ("0" + dt.getDate()).slice(-2);
      let month = ("0" + (dt.getMonth() + 1)).slice(-2);
      let year = dt.getFullYear();
      let hour = ("0" + dt.getHours()).slice(-2);
      let minute = ("0" + dt.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hour}:${minute}`;
    }

    const formatRange = (dates) => {
      let from = dates[0];
      let to = dates[1];

      const dayFrom = from.getDate();
      const monthFrom = from.getMonth() + 1;
      const yearFrom = from.getFullYear();
      const hourFrom = from.getHours();
      const minuteFrom = from.getMinutes();

      const dayTo = to.getDate();
      const monthTo = to.getMonth() + 1;
      const yearTo = to.getFullYear();
      const hourTo = to.getHours();
      const minuteTo = to.getMinutes();

      return `${dayFrom}.${monthFrom}.${yearFrom} ${hourFrom}:${minuteFrom} - ${dayTo}.${monthTo}.${yearTo} ${hourTo}:${minuteTo}`
    }

    return {
      editing,
      creatingOffer,
      ship,
      pictures,
      availabilityInterval,
      offer,
      offerInterval,
      tags,
      toggleEdit,
      toggleCreatingOffer,
      cancel,
      saveEdit,
      createOffer,
      updateTags,
      formatRange
    }
  }
}
</script>

<style scoped>

</style>