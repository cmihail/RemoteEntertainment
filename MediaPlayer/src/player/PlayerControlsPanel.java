package player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import proto.ProtoPlayer.Command.Type;

import client.PlayerCommand;
import client.PlayerCommandExecutor;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * Defines the player controls used in {@link PlayerView}.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerControlsPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private final ScheduledExecutorService executorService =
      Executors.newSingleThreadScheduledExecutor();

  private final PlayerCommandExecutor commandExecutor;
  private final MediaPlayer mediaPlayer;


  // Guard to prevent the position slider firing spurious change events when
  // the position changes during play-back - events are only needed when the
  // user actually drags the slider and without the guard the play-back
  // position will jump around
  private boolean setPositionValue;
  private boolean isPlaying;

  private JLabel timeLabel;
  private JSlider positionSlider;
  private JLabel chapterLabel;

  private JButton previousChapterButton;
  private JButton rewindButton;
  private JButton stopButton;
  private JButton playPauseButton;
  private JButton fastForwardButton;
  private JButton nextChapterButton;

  private JButton toggleMuteButton;
  private JSlider volumeSlider;
  private JButton fullScreenButton;

  private ImageIcon playIcon;
  private ImageIcon pauseIcon;

  public PlayerControlsPanel(PlayerCommandExecutor commandExecutor, MediaPlayer mediaPlayer) {
    this.commandExecutor = commandExecutor;
    this.mediaPlayer = mediaPlayer;

    createUI();

    // Define the thread that will update the players controls based on movie running.
    executorService.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        update();
      }
    }, 0L, 1L, TimeUnit.SECONDS);
  }

  /**
   * Creates the UI which consists in creating the player controls, positioning them
   * and registering their listeners.
   */
  private void createUI() {
    createControls();
    layoutControls();
    registerListeners();
  }

  /**
   * Creates player controls.
   */
  private void createControls() {
    timeLabel = new JLabel("hh:mm:ss");
    positionSlider = new JSlider();
    positionSlider.setMinimum(0);
    positionSlider.setMaximum(100);
    positionSlider.setValue(0);
    positionSlider.setToolTipText("Position");
    chapterLabel = new JLabel("00/00");

    previousChapterButton = createButton(createIcon("icons/control_start_blue.png"),
        "Go to previous chapter");
    rewindButton = createButton(createIcon("icons/control_rewind_blue.png"), "Skip back");
    stopButton = createButton(createIcon("icons/control_stop_blue.png"), "Stop");
    playIcon = createIcon("icons/control_play_blue.png");
    pauseIcon = createIcon("icons/control_pause_blue.png");
    playPauseButton = createButton(playIcon, "Play/pause");
    fastForwardButton = createButton(createIcon("icons/control_fastforward_blue.png"),
        "Skip forward");
    nextChapterButton = createButton(createIcon("icons/control_end_blue.png"),
        "Go to next chapter");

    toggleMuteButton = createButton(createIcon("icons/sound_mute.png"), "Toggle Mute");
    volumeSlider = new JSlider();
    volumeSlider.setOrientation(JSlider.HORIZONTAL);
    volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
    volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
    volumeSlider.setPreferredSize(new Dimension(100, 40));
    volumeSlider.setToolTipText("Change volume");
    fullScreenButton = createButton(createIcon("icons/image.png"), "Toggle full-screen");
  }

  private JButton createButton(ImageIcon icon, String toolTipText) {
    JButton button = new JButton();
    button.setIcon(icon);
    button.setToolTipText(toolTipText);
    return button;
  }

  private ImageIcon createIcon(String imagePath) {
    return new ImageIcon(getClass().getClassLoader().getResource(imagePath));
  }

  /**
   * Sets the positions of player controls in the panel.
   */
  private void layoutControls() {
    // Main container
    setBorder(new EmptyBorder(4, 4, 4, 4));
    setLayout(new BorderLayout());

    // Top Panel
    JPanel positionPanel = new JPanel();
    positionPanel.setLayout(new GridLayout(1, 1));
    positionPanel.add(positionSlider);

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout(8, 0));
    topPanel.add(timeLabel, BorderLayout.WEST);
    topPanel.add(positionPanel, BorderLayout.CENTER);
    topPanel.add(chapterLabel, BorderLayout.EAST);
    add(topPanel, BorderLayout.NORTH);

    // Bottom Panel
    JPanel bottomLeftPanel = new JPanel();
    bottomLeftPanel.setLayout(new FlowLayout());
    bottomLeftPanel.add(previousChapterButton);
    bottomLeftPanel.add(rewindButton);
    bottomLeftPanel.add(stopButton);
    bottomLeftPanel.add(playPauseButton);
    bottomLeftPanel.add(fastForwardButton);
    bottomLeftPanel.add(nextChapterButton);

    JPanel bottomRightPanel = new JPanel();
    bottomRightPanel.setLayout(new FlowLayout());
    bottomRightPanel.add(toggleMuteButton);
    bottomRightPanel.add(volumeSlider);
    bottomRightPanel.add(fullScreenButton);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
    bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Registers listeners for player controls.
   */
  private void registerListeners() {
    mediaPlayer.addMediaPlayerEventListener(
        new MediaPlayerEventAdapter() {
          @Override
          public void playing(MediaPlayer mediaPlayer) {
            volumeSlider.setValue(mediaPlayer.getVolume());
          }
        });

    positionSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if(!positionSlider.getValueIsAdjusting() && !setPositionValue) {
          float positionValue = positionSlider.getValue() / 100.0f;
          commandExecutor.executeCommand(
              new PlayerCommand(Type.SET_POSITION, positionValue + ""), true);
        }
      }
    });

    previousChapterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.PREVIOUS), true);
      }
    });

    rewindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.BACKWARD), true);
      }
    });

    stopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.STOP), true);
      }
    });

    playPauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean isPlaying = mediaPlayer.isPlaying();
        PlayerControlsPanel.this.isPlaying = isPlaying;
        if (isPlaying) {
          commandExecutor.executeCommand(new PlayerCommand(Type.PAUSE), true);
          playPauseButton.setIcon(pauseIcon);
        } else {
          commandExecutor.executeCommand(new PlayerCommand(Type.PLAY), true);
          playPauseButton.setIcon(playIcon);
        }
      }
    });

    fastForwardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.FORWARD), true);
      }
    });

    nextChapterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.NEXT), true);
      }
    });

    toggleMuteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.MUTE), true);
      }
    });

    volumeSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if(!source.getValueIsAdjusting()) {
          commandExecutor.executeCommand(
              new PlayerCommand(Type.SET_VOLUME, source.getValue() + ""), true);
        }
      }
    });

    fullScreenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        commandExecutor.executeCommand(new PlayerCommand(Type.TOGGLE_FULL_SCREEN), true);
      }
    });
  }

  /**
   * Updates play/pause button, time, position slider and volume slider.
   */
  private void update() {
    final boolean isPlaying = mediaPlayer.isPlaying();

    final long time = mediaPlayer.getTime();

    final long duration = mediaPlayer.getLength();
    final int position = duration > 0 ? (int)Math.round(100.0 * time / duration) : 0;

    final int chapter = mediaPlayer.getChapter();
    final int chapterCount = mediaPlayer.getChapterCount();

    // Updates to user interface components must be executed on the Event Dispatch Thread.
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (PlayerControlsPanel.this.isPlaying != isPlaying) {
          PlayerControlsPanel.this.isPlaying = isPlaying;
          if (isPlaying) {
            playPauseButton.setIcon(playIcon);
          } else {
            playPauseButton.setIcon(pauseIcon);
          }
        }

        updateTime(time);
        updatePosition(position);
        updateChapter(chapter, chapterCount);
      }
    });
  }

  private void updateTime(long millis) {
    String s = String.format("%02d:%02d:%02d",
      TimeUnit.MILLISECONDS.toHours(millis),
      TimeUnit.MILLISECONDS.toMinutes(millis) -
        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
      TimeUnit.MILLISECONDS.toSeconds(millis) -
        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
    );
    timeLabel.setText(s);
  }

  private void updatePosition(int value) {
    // Set the guard to stop the update from firing a change event
    setPositionValue = true;
    positionSlider.setValue(value);
    setPositionValue = false;
  }

  private void updateChapter(int chapter, int chapterCount) {
    String s = chapterCount != -1 ? (chapter+1) + "/" + chapterCount : "-";
    chapterLabel.setText(s);
    chapterLabel.invalidate();
    validate();
  }
}
