$(document).ready(function () {
        $('#dateBegin').val(formatDateToString(new Date()));
        $('#dateEnd').val(formatDateToString(new Date()));
        $('#timeBegin').val("00:00");
        $('#timeEnd').val("23:59");

        $('#getBoardBtn').click(function () {
            getTrips();
        });
    }
);


function getTrips() {
    var dateBegin = readDateInput($('#dateBegin'));
    var dateEnd = readDateInput($('#dateEnd'));
    var timeBegin = readTimeInput($('#timeBegin'), "00:00");
    var timeEnd = readTimeInput($('#timeEnd'), "23:59");
    var stationBegin = $('#stationBegin').find('option:selected').val();
    var stationEnd = $('#stationEnd').find('option:selected').val();

    $.post('/SBB/reqtrip/get',
        {departureStationId: stationBegin, departureDate: dateBegin, departureTime: "00:00",
            arriveStationId: stationEnd, arriveDate: dateEnd, arriveTime: "23:59"},
        function (response) {
            response = response.trim();
            if (isError(response)) {
                return;
            }
            $('.remove').remove();
            $('#tripTable').append(response);
        }
    );
}

function buyTicketDialog(departureId, arriveId, freePlaces) {
    if (freePlaces == 0) {
        popupError("This trip is out of free places!");
    }
    var $passengerForm = $('#passengerForm').clone().removeAttr('id');
    BootstrapDialog.show({
        title: 'Buy a ticket',
        message: $passengerForm,
        buttons: [
            {
                label: 'OK',
                action: function (dialog) {
                    var passenger = makeData($passengerForm);
                    alert(passenger.lastName);
                    $.post('/SBB/ticket/buy',
                        {firstName: passenger.firstName, lastName: passenger.lastName, birthDate: passenger.birthDate,
                            departureBoardId: departureId, arriveBoardId: arriveId},
                        function(response) {
                            if (!isError(response)) {
                                dialog.close();
                                popupSuccess(response);
                            }
                        }
                    );
                    /*$.ajax({
                     url: "/SBB/tickets/buy",
                     success: function () {
                     $('#' + id).remove();
                     dialog.setMessage('Delete success');
                     dialog.close();
                     },
                     error: function (error) {
                     dialog.setMessage(error);
                     }
                     });*/
                }
            },
            {
                label: 'Cancel',
                action: function () {
                    this.close();
                }
            }
        ]
    });
}

function makeData($passengerForm) {
    var boardTripDTO = {};
    var firstName = $passengerForm.find('#inputFN').val();
    var lastName = $passengerForm.find('#inputLN').val();
    var dateInput = $passengerForm.find('#inputDate');
    var birthDate = dateInput.val();
    if (birthDate === '') {
        alert('Invalid date of birth');
        dateInput.focus();
        return;
    }
    birthDate = cellsToDate(birthDate);
    boardTripDTO.firstName = firstName;
    boardTripDTO.lastName = lastName;
    boardTripDTO.birthDate = birthDate;
    //alert(firstName + ' ' + lastName + ' ' + birthDate % (1000 * 86400));
    return boardTripDTO;
}
