package br.ufrn.imd.markethub.service.wallet.exception;

import br.ufrn.imd.markethub.service.wallet.dto.ErrorDto;
import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

  private final ErrorDto error;

  public ServerException(ErrorDto error) {
    super(error.getMessage());
    this.error = error;
  }

  public static ServerException notFound() {
    return new ServerException(ErrorDto.builder()
            .code(404)
            .message("Not Found")
            .build());
  }
}
