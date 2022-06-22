<template>
  <div class="left-bar">
    <h5 class="left-bar-heading">Reservations</h5>
  </div>

  <div class="home-entities-div">
    <div class="w-100" style="text-align: center;" v-if="reservations.length==0">
      <h4 style="font-weight: bold;">No reservations to show yet</h4>
    </div>
    <div class="home-list-entities" v-else v-for="reservation in reservations" :key="reservation.id">
      <div class="home-entity-picture">
        <img v-bind:src="'data:image/jpeg;base64,' + reservation.img" v-bind:alt="reservation.id" style="width: 200px; height: 150px; cursor: pointer">
      </div>
      <div class="home-entity-content">
        <h4 style="font-weight: bold; cursor: pointer">{{ reservation.rentingEntityName }}</h4>
        <div class="home-entity-description">
          <p>From: {{ reservation.start }}</p>
          <p>To: {{ reservation.end }}</p>
        </div>
      </div>
      <div class="home-entity-price h-100 d-flex">
        <div class="align-self-center w-100 p-4">
          <button @click="openClientPreview(reservation)" type="button" class="custom-btn button-primary d-block w-100">Client Profile</button>
          <button v-if="isInProgress(reservation)" @click="toggleMakingReservation(reservation)" class="custom-btn button-primary d-block w-100" data-bs-toggle="modal" data-bs-target="#reserveModal">Reserve Again</button>
          <button v-if="isFinished(reservation)" @click="selectReservation(reservation)" type="button" class="custom-btn button-primary d-block w-100" data-bs-toggle="modal" data-bs-target="#reportModal">Write Report</button>
        </div>
      </div>

      <div class="modal fade" id="reserveModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title mx-auto">Make Another Reservation</h5>
            </div>
            <div class="modal-body">
              <div v-if="loadedCalendar">
                <v-date-picker style="width: 100%" v-model="range" mode='dateTime' color="blue" is24hr :minute-increment="30" is-range  :min-date=minDate :max-date=maxDate :attributes='attributes'>
                </v-date-picker>
              </div>
              <div style="margin-top: 10px" v-for="service in additionalServices" :key="service">
                <span style="margin-right: 10px">{{service}} </span>
                <input type="checkbox" v-model="selectedServices" :value="service" checked/>
              </div>
            </div>

            <div class="modal-footer">
              <button type="button" class="custom-btn button-close" data-bs-dismiss="modal">Cancel</button>
              <button type="button" class="custom-btn button-primary" @click="makeReservation" data-bs-dismiss="modal">Make Reservation</button>
            </div>
          </div>
        </div>
      </div>

      <div class="modal fade" id="reportModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title mx-auto">Reservation Report</h5>
            </div>
            <div class="modal-body">
              <div class="form-floating mb-3">
                <textarea style="height: 100px" v-model="report.text" type="text" class="form-control" id="reportText" placeholder="How did the reservation go?"></textarea>
                <label for="reportText">How did the reservation go?</label>
              </div>
              <label class="me-3">How was your experience with the client?</label>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="reportType" id="reportType1" value="GOOD" v-model="report.type">
                <label class="form-check-label" for="reportType1">Good</label>
              </div>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="reportType" id="reportType2" value="BAD" v-model="report.type">
                <label class="form-check-label" style="background-color: white" for="reportType2">Bad</label>
              </div>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="reportType" id="reportType3" value="NO_SHOW" v-model="report.type">
                <label class="form-check-label" style="background-color: white" for="reportType3">They didn't show up</label>
              </div>
            </div>

            <div class="modal-footer">
              <button type="button" class="custom-btn button-close" data-bs-dismiss="modal">Cancel</button>
              <button type="button" class="custom-btn button-primary" @click="sendReport" data-bs-dismiss="modal">Send Report</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="alertModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5>{{alertTitle}}</h5>
        </div>
        <div class="modal-body">
          <p>{{alertText}}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="custom-btn button-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {ref, onMounted, computed} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";
import {Modal} from "bootstrap";

export default {
  name: "OwnerReservations",
  setup() {
    const router = useRouter();

    let owner = ref({});
    let reservations = ref([{img: ""}]);
    let selectedReservation = ref({});
    let entityTypeInfo = ref({
      urlPart: "",
      ownerUrlPart: "",
      entityUrlPart: "",
      entityName: ""
    });
    let report = ref({
      text: "",
      type: "GOOD",
      reservationId: null,
      clientId: null,
      submitterId: null,
      reviewed: false
    });
    let takenDates = ref([]);
    let range = ref({
      start: null,
      end: null
    });
    let minDate = ref(null);
    let maxDate = ref(null);
    let bars = ref([]);
    let takenDatesPoppers = ref([]);
    // let incId = ref(takenDatesPoppers.value.length);
    let loadedCalendar = ref(false);
    let additionalServices = ref([]);
    let selectedServices = ref([]);
    let rentingEntity = ref([]);

    let alertTitle = ref("Report Sent");
    let alertText = ref("Report sent successfully");

    const attributes = computed(() => {
      return [
        ...bars.value,
        ...takenDatesPoppers.value.map(todo => ({
          dates: todo.dates,
          dot: {
            color: todo.color,
            class: todo.isComplete ? 'opacity-75' : ''
          },
          popover: {
            label: todo.description
          },
          customData: todo
        }))
      ];
    });

    onMounted(() => {
      minDate.value = null;
      maxDate.value = null;
      loadedCalendar.value = false;

      axios
          .get("/api/users/loggedUser",
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            owner.value = response.data;
            if(response.data.accountType === "VH_OWNER") {
              entityTypeInfo.value = {
                urlPart: "vacationHouseOwners/loggedVacationHouseOwner",
                ownerUrlPart: "vacationHouseOwners",
                entityUrlPart: "vacationHouses",
                entityName: "Vacation house"
              };
            } else if (response.data.accountType === "SH_OWNER") {
              entityTypeInfo.value = {
                urlPart: "shipOwners/loggedShipOwner",
                ownerUrlPart: "shipOwners",
                entityUrlPart: "ships",
                entityName: "Ship"
              };
            } else {
              router.push({name: "homePage"});
            }
            resetReport();
            loadData();
          })
          .catch(error => {
            console.log(`OwnerHome catch users/loggedUser: ${error.response}`);
          });
    });

    function loadData() {
      axios
          .get(`api/${entityTypeInfo.value.urlPart}/reservations`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            reservations.value = response.data;
            console.log(reservations.value);
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    function stringToDateTime(s) {
      if(s == null){
        return new Date();
      }

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

    function isFinished(reservation) {
      let finished = false
      if (reservation != undefined && reservation.end != undefined) {
        let endDate = stringToDateTime(reservation.end);
        let currentTime = new Date();
        finished = (currentTime > endDate) && reservation.reviewed === false;
      }
      return finished;
    }

    function isInProgress(reservation) {
      let finished = false
      if (reservation != undefined && reservation.start != undefined && reservation.end != undefined) {
        let startDate = stringToDateTime(reservation.start);
        let endDate = stringToDateTime(reservation.end);
        let currentTime = new Date();
        finished = (startDate < currentTime) && (endDate > currentTime);
      }
      return finished;
    }

    function resetReport() {
      report.value = {
        text: "",
        type: "GOOD",
        reservationId: null,
        clientId: null,
        submitterId: null,
        reviewed: false
      }
    }

    function selectReservation(reservation) {
      selectedReservation.value = reservation;
    }

    function sendReport() {
      report.value.reservationId = selectedReservation.value.id;
      report.value.clientId = selectedReservation.value.clientId;
      report.value.submitterId = owner.value.id;
      alert(report.value.reservationId);

      let alertModal = new Modal(document.getElementById('alertModal'), {});

      if (report.value.text == "") {
        alertTitle.value = "Report Not Sent";
        alertText.value = "You must write something in the report";
        alertModal.show();
        return;
      }
      axios
          .post(`/api/clients/loggedOwner/sendReservationReport`, report.value,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            if (response.data === true) {
              alertTitle.value = "Report Sent";
              alertText.value = "Report sent successfully";
            } else {
              alertTitle.value = "Report Not Sent";
              alertText.value = "Something went wrong";
            }
            alertModal.show();
            loadData();
            resetReport();
          });
    }

    function openClientPreview(reservation) {
      axios
          .get(`/api/clients/${reservation.clientId}`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            router.push(`/user/${response.data.username}`);
          })
    }

    function generateBar(takenDTO) {
      let color = "red";
      if (takenDTO.type == "Offer") {
        color = "blue";
      }
      let datesBars = [];
      let takenTo = stringToDateTime(takenDTO.takenTo);
      for (let d = stringToDateTime(takenDTO.takenFrom); d <= takenTo; d.setDate(d.getDate() + 1)) {
        const date = new Date(d);
        datesBars.push(date);
      }
      const bar = {
        dates: datesBars,
        bar: color
      }
      bars.value.push(bar)
    }

    function generatePopover(takenDTO) {
      let col = "red";
      if (takenDTO.type == "Offer") {
        col = "blue";
      }
      const takenStart = {
        description: takenDTO.startsAt,
        isComplete: false,
        dates: stringToDateTime(takenDTO.takenFrom),
        color: col
      };
      const takenEnd = {
        description: takenDTO.endsAt,
        isComplete: false,
        dates: stringToDateTime(takenDTO.takenTo),
        color: col
      };
      takenDatesPoppers.value.push(takenStart);
      takenDatesPoppers.value.push(takenEnd);
    }

    function takeCalendar(takenDatesParam) {
      bars.value = [];
      takenDatesPoppers.value = [];
      takenDatesParam.forEach(generateBar);
      takenDatesParam.forEach(generatePopover);
      takenDates.value = takenDatesParam;
    }

    function toggleMakingReservation(reservation) {
      selectReservation(reservation);
      axios
          .get(`/api/${entityTypeInfo.value.entityUrlPart}/anyUser/${reservation.rentingEntityId}`)
          .then( response => {
            rentingEntity.value = response.data;


            axios
                .get(`api/clients/rentingEntityAvailability/${rentingEntity.value.id}`,
                    { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
                .then(response => {
                  takeCalendar(response.data);
                  const availableFromDate = stringToDateTime(rentingEntity.value.availableFrom);
                  const availableToDate = stringToDateTime(rentingEntity.value.availableTo);
                  const now = new Date();

                  if (availableFromDate <= now) {
                    minDate.value = now;
                  } else {
                    minDate.value = availableFromDate;
                  }

                  if (availableToDate <= now) {
                    maxDate.value = now;
                  } else {
                    maxDate.value = availableToDate;
                  }

                  let availableFrom = `${entityTypeInfo.value.entityName} available from ${minDate.value.getHours()}:${minDate.value.getMinutes()}`;
                  let availableTo = `${entityTypeInfo.value.entityName} available to ${maxDate.value.getHours()}:${maxDate.value.getMinutes()}`;

                  if (availableToDate < now && availableFromDate < now) {
                    availableFrom = "Not available";
                    availableTo = "Not available";
                  }

                  const takenStart = {
                    description: availableFrom,
                    isComplete: false,
                    dates: minDate.value,
                    color: "green"
                  };

                  const takenEnd = {
                    description: availableTo,
                    isComplete: false,
                    dates: maxDate.value,
                    color: "green"
                  }

                  takenDatesPoppers.value.push(takenStart);
                  takenDatesPoppers.value.push(takenEnd);

                  loadedCalendar.value = true;

                })

            let servicesPath = `/api/${entityTypeInfo.value.ownerUrlPart}/getServices/${rentingEntity.value.ownerId}`;
            axios
                .get(servicesPath,
                    { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
                .then(response => additionalServices.value = response.data);
          });

    }

    function freeSlot(start, end){
      for (let i = 0; i < takenDates.value.length; i++) {
        const takenFrom = stringToDateTime(takenDates.value[i].takenFrom);
        const takenTo = stringToDateTime(takenDates.value[i].takenTo);
        if( (start >= takenFrom && end <=takenTo) ||
            (start <= takenFrom && end >= takenTo) ||
            (start>=takenFrom && start<=takenTo) ||
            (end <= takenTo && end >= takenFrom) ||
            start < minDate.value || end > maxDate.value){
          return false;
        }
      }
      return true;
    }

    function makeReservation() {
      const start = range.value.start;
      const end = range.value.end;

      if (freeSlot(start, end)) {
        let clientLimit = 0;
        if(entityTypeInfo.value.entityName === "Vacation house"){
          clientLimit = rentingEntity.value.roomsQuantity * rentingEntity.value.bedsPerRoom;
        }else{
          clientLimit = rentingEntity.value.clientLimit;
        }
        if(minDate.value == maxDate.value){
          return;
        }
        const newReservation = {
          "place" : rentingEntity.value.address,
          "clientLimit" : clientLimit,
          "additionalServices" : selectedServices.value,
          "price" : rentingEntity.value.priceList,
          "rentingEntityId" : rentingEntity.value.id,
          "start": start,
          "end": end
        };
        axios.post(`/api/clients/makeReservationFull/${selectedReservation.value.clientId}`, newReservation,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
            .then( response => {
              if(response.data==true){
                alert("Reservation made");
              }else{
                if(response.status == 423){
                  alert("Try later.");
                }else{
                  alert("Error");
                }
              }
            }
        );
        loadData();
      }else{
        this.$toast.error("Select free date");
      }
    }

    return {
      reservations,
      selectedReservation,
      report,
      alertTitle,
      alertText,
      takenDates,
      range,
      minDate,
      maxDate,
      attributes,
      loadedCalendar,
      additionalServices,
      selectedServices,
      isFinished,
      isInProgress,
      selectReservation,
      sendReport,
      openClientPreview,
      toggleMakingReservation,
      makeReservation
    }
  }
}
</script>

<style scoped>

</style>