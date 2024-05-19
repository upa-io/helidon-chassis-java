import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
    var server_list = ["host.docker.internal:8081", "host.docker.internal:8082", "host.docker.internal:8083"]
    const context_root = "/open/upaio/principal/helidon/v1"
    var endpoint_list = [context_root + "/memory-intensive", context_root + "/work-cpu", context_root + "/fast-response"]
    server_list.forEach(function (server) {
        endpoint_list.forEach(function (endpoint) {
            http.get("http://" + server + endpoint);
        });
    });
    sleep(0.5);
}