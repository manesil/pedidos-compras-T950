package br.com.ada.itau950.pedidos.compras.controller;

import br.com.ada.itau950.pedidos.compras.dto.ProdutoResponseDTO;
import br.com.ada.itau950.pedidos.compras.dto.ProdutoSaveRequestDTO;
import br.com.ada.itau950.pedidos.compras.dto.ProdutoSaveResponseDTO;
import br.com.ada.itau950.pedidos.compras.entity.Produto;
import br.com.ada.itau950.pedidos.compras.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<ProdutoSaveResponseDTO> save(@RequestBody ProdutoSaveRequestDTO produtoRequest) {

        log.info(produtoRequest.toString());

        Produto produto = new Produto();
        produto.setFoto(produtoRequest.getFoto());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setPreco(produtoRequest.getPreco());
        produto.setNome(produtoRequest.getNome());
        produto.setDesconto(produtoRequest.getDesconto());

        //save
        produtoRepository.save(produto);

        ProdutoSaveResponseDTO produtoResponse = new ProdutoSaveResponseDTO();
        produtoResponse.setId(produto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }

    @GetMapping(value = "/{idProduto}")
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable(value = "idProduto") Long id) {

        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            ProdutoResponseDTO produtoDto = new ProdutoResponseDTO();
            produtoDto.setNome(produto.get().getNome());
            produtoDto.setDescricao(produto.get().getDescricao());
            produtoDto.setPreco(produto.get().getPreco());
            produtoDto.setId(id);
            return ResponseEntity.ok(produtoDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{idProduto}/{qtdeEstoque}")
    public ResponseEntity updateEstoque(@PathVariable Long idProduto, @PathVariable Integer qtdeEstoque) {
        //update
        log.info("idProduto: {} qtde: {}", idProduto, qtdeEstoque);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<String> delete(@PathVariable Long idProduto) {

        if (idProduto == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID nao encontrado");
        }

        // delete

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> findAll() {
        //select
        return ResponseEntity.ok(new ArrayList<>());
    }

}