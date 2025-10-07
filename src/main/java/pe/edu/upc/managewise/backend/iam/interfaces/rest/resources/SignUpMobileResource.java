package pe.edu.upc.managewise.backend.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpMobileResource(String username, String password, List<String> roles) {
}
