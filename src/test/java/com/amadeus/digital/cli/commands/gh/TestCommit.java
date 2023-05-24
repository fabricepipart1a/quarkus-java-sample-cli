package com.amadeus.digital.cli.commands.gh;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestCommit {
/*
  private static final List<String> SHA_LIST = Arrays.asList("6d8f3e29ff00d8a0723f56a91a4af1c70b9a5f5c",
      "c9a7b8e5f00d8e67b34a23c98a4af1c70b9a5f5c",
      "fb1b6a85f00d8e67b34a23c98a4af1c70b9a5f5c",
      "7ca6f32bff00d8a0723f56a91a4af1c70b9a5f5c",
      "45c6e9ea7f00d8e67b34a23c98a4af1c70b9a5f5c",
      "a3b8c7d9ff00d8a0723f56a91a4af1c70b9a5f5c",
      "e2d3f4a8ff00d8e67b34a23c98a4af1c70b9a5f5c",
      "b7c8d9f6f00d8a0723f56a91a4af1c70b9a5f5c",
      "9e8d7c6bff00d8e67b34a23c98a4af1c70b9a5f5c",
      "5f4e3d2aff00d8a0723f56a91a4af1c70b9a5f5c");
  @Mock
  Logger log;
  @Mock
  Logger console;
  @Mock
  ImpactedCommitService commitScopeService;
  @Captor
  private ArgumentCaptor<String> logsCaptor;
  private Commit commit;

  @BeforeEach
  public void setup() {
    commit = new Commit(log, console, commitScopeService);
  }

  @Test
  void errorRaisedIfGitNotAvailable() throws Exception {
    when(commitScopeService.gitAvailableInPath()).thenReturn(false);
    Assertions.assertThat(commit.call()).isEqualTo(CommandLine.ExitCode.SOFTWARE);
  }

  @Test
  void noErrorRaisedIfNoShaFound() throws Exception {
    when(commitScopeService.gitAvailableInPath()).thenReturn(true);
    when(commitScopeService.getListImpactedCommits(any(), any())).thenReturn(Arrays.asList("\n"));
    Assertions.assertThat(commit.call()).isEqualTo(CommandLine.ExitCode.OK);
    when(commitScopeService.getListImpactedCommits(any(), any())).thenReturn(Arrays.asList(""));
    Assertions.assertThat(commit.call()).isEqualTo(CommandLine.ExitCode.OK);
    when(commitScopeService.getListImpactedCommits(any(), any())).thenReturn(null);
    Assertions.assertThat(commit.call()).isEqualTo(CommandLine.ExitCode.OK);
  }

  @Test
  void printsShaNames() throws Exception {
    when(commitScopeService.gitAvailableInPath()).thenReturn(true);
    when(commitScopeService.getListImpactedCommits(any(), any())).thenReturn(SHA_LIST);
    Assertions.assertThat(commit.call()).isEqualTo(CommandLine.ExitCode.OK);
    verify(console, times(10)).info(logsCaptor.capture());
    Assertions.assertThat(logsCaptor.getAllValues()).contains(SHA_LIST.get(5));
  }
*/
}