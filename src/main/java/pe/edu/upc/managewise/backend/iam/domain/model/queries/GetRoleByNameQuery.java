package pe.edu.upc.managewise.backend.iam.domain.model.queries;

import pe.edu.upc.managewise.backend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
