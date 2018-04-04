package com.handicraft.api.controller;

import com.handicraft.core.dto.CommentDto;
import com.handicraft.core.service.CommentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/comments")
    @ApiOperation(value = "", notes = "insert comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity createComments(@RequestBody CommentDto commentDto) {
        commentService.create(commentDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/comments")
    @ApiOperation(value = "", notes = "find comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findComments(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return ResponseEntity.ok(commentService.find(page, perPage));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/comments")
    @ApiOperation(value = "", notes = "modify comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateComments(@RequestBody List<CommentDto> commentDtos) {
        commentService.update(commentDtos);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/comments")
    @ApiOperation(value = "", notes = "delete comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeComments() {
        commentService.removeAll();
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/comments/{cid}")
    @ApiOperation(value = "", notes = "find comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findComment(@PathVariable("cid") long cid) {
        return ResponseEntity.ok(commentService.findByCid(cid));
    }

    @PutMapping("/comments/{cid}")
    @ApiOperation(value = "", notes = "modify comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateComment(@PathVariable("cid") long cid, @RequestBody CommentDto commentDto) {
        commentService.updateOne(commentDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/comments/{cid}")
    @ApiOperation(value = "", notes = "delete comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeComment(@PathVariable("cid") long cid) {
        commentService.removeByCid(cid);
        return ResponseEntity.ok().build();
    }
}
