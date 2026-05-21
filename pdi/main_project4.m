% ==========================================================================
% main_project4.m  –  Project #4: imagePad4e demo script
% Disciplina: Processamento Digital de Imagens – UFAM (2026)
% Author    : Pedro Victor dos Santos Oliveira
%
% Description:
%   Loads testpattern1024.tif, applies zero-padding and replicate-padding
%   using imagePad4e(), displays the results side-by-side, and saves the
%   output images to the images/ subfolder.
% ==========================================================================

clear; clc; close all;

%% 1. Load input image
imgFile = 'testpattern1024.tif';
if ~isfile(imgFile)
    error('Image file "%s" not found. Place it in the same folder.', imgFile);
end
f = imread(imgFile);
fprintf('Image loaded: %s  [%d x %d]\n', imgFile, size(f,1), size(f,2));

%% 2. Padding parameters
r = 100;   % rows to add above and below
c = 100;   % columns to add to the left and right
fprintf('Padding: r=%d rows, c=%d columns\n', r, c);

%% 3. Apply padding
g_zeros     = imagePad4e(f, r, c, 'zeros');
g_replicate = imagePad4e(f, r, c, 'replicate');
fprintf('Output size: [%d x %d]\n', size(g_zeros,1), size(g_zeros,2));

%% 4. Display results
figure('Name','Project #4 – Image Padding', 'NumberTitle','off', ...
       'Position',[100 100 1400 450]);

subplot(1,3,1);
imshow(f);
title(sprintf('Original (%dx%d)', size(f,1), size(f,2)), 'FontSize',12);

subplot(1,3,2);
imshow(g_zeros);
title(sprintf('Zero Padding (%dx%d)', size(g_zeros,1), size(g_zeros,2)), 'FontSize',12);

subplot(1,3,3);
imshow(g_replicate);
title(sprintf('Replicate Padding (%dx%d)', size(g_replicate,1), size(g_replicate,2)), 'FontSize',12);

sgtitle('imagePad4e – Comparison', 'FontSize',14, 'FontWeight','bold');

%% 5. Save images
if ~isfolder('images'), mkdir('images'); end
imwrite(f,           'images/original.png');
imwrite(g_zeros,     'images/zeros_padded.png');
imwrite(g_replicate, 'images/replicate_padded.png');
fprintf('Images saved to images/\n');
